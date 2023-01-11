package com.ot.playground.techtest.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ot.playground.techtest.R
import com.ot.playground.techtest.databinding.FragmentFeedBinding
import com.ot.playground.techtest.view.adapter.FeedAdapter
import com.ot.playground.techtest.viewmodel.FeedViewModel
import com.ot.playground.techtest.viewmodel.FeedViewModel.ViewEvent.Error
import com.ot.playground.techtest.viewmodel.FeedViewModel.ViewEvent.NavigateToDetailScreen
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
        viewModel.getGithubRepos()
        return binding.root
    }

    private fun navigateToDetailScreen(id: Int) {
        viewModel.navigateToDetailScreen(id)
    }

    private fun initObservers() {
        viewModel.viewEvent.observe(viewLifecycleOwner) { event ->
            Log.d(this::class.java.simpleName, "viewEvent: $event")
            when (event) {
                Error -> Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show()
                is NavigateToDetailScreen -> findNavController().navigate(FeedFragmentDirections.actionFeedFragmentToDetailFragment(event.repoName, event.repoOwner))
            }

        }


        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            Log.d(this::class.simpleName, "$state")
            adapter.submitList(state.githubRepos)
        }
    }
}