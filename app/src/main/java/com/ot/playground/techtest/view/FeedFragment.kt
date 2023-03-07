package com.ot.playground.techtest.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.navigation.fragment.findNavController
import com.ot.playground.techtest.R
import com.ot.playground.techtest.databinding.FragmentFeedBinding
import com.ot.playground.techtest.utils.coroutines.runInLifecycle
import com.ot.playground.techtest.view.adapter.FeedAdapter
import com.ot.playground.techtest.viewmodel.FeedViewModel
import com.ot.playground.techtest.viewmodel.FeedViewModel.ViewEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : Fragment() {

    private val viewModel: FeedViewModel by viewModel()
    private lateinit var binding: FragmentFeedBinding
    private lateinit var adapter: FeedAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFeedBinding.inflate(layoutInflater, container, false)
        adapter = FeedAdapter { navigateToDetailScreen(it) }
        binding.fieldList.adapter = adapter
        initObservers()
        return binding.root
    }

    private fun navigateToDetailScreen(id: Int) {
        viewModel.onClickRepository(id)
    }


    private fun initObservers() {
        viewLifecycleOwner.runInLifecycle(STARTED) {
            viewModel.viewState.collect { state ->
                Log.d(this::class.simpleName, "viewState: $state")
                adapter.submitList(state.githubRepos)
                with(state.viewEvent) {
                    when (this) {
                        is ViewEvent.Initial -> {
                            viewModel.getGithubRepos()
                            viewModel.gitReposFetched()
                        }
                        is ViewEvent.Error -> {
                            Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show()
                            viewModel.errorMessageShown()
                        }
                        is ViewEvent.RepositoryClicked -> {
                            findNavController().navigate(
                                FeedFragmentDirections.actionFeedFragmentToDetailFragment(repoName, repoOwner)
                            )
                            viewModel.navigatedToDetailScreen()
                        }
                        is ViewEvent.None -> {
                            // Nothing required.
                        }
                    }
                }
            }
        }
    }
}
