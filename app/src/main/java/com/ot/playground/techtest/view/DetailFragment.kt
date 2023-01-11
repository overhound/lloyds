package com.ot.playground.techtest.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ot.playground.techtest.R
import com.ot.playground.techtest.databinding.FragmentDetailBinding
import com.ot.playground.techtest.view.adapter.TopicsAdapter
import com.ot.playground.techtest.viewmodel.DetailViewModel
import com.ot.playground.techtest.viewmodel.DetailViewModel.ViewEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModel()
    private val fragmentArgs: DetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailBinding
    private lateinit var adapter: TopicsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        adapter = TopicsAdapter(mutableListOf())
        binding.topics.adapter = adapter
        initObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showRepoInfo()
    }

    private fun initObservers() {
        viewModel.viewEvent.observe(viewLifecycleOwner) { event ->
            Log.d(this::class.java.simpleName, "viewEvent: $event")
            when (event) {
                ViewEvent.Error -> Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show()
            }

        }


        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            Log.d(this::class.simpleName, "$state")
            adapter.submitList(state.githubRepo.topics)
            binding.name.text = state.githubRepo.name
            binding.description.text = state.githubRepo.description
            with(requireContext()) {
                binding.issues.text = getString(R.string.issues, state.githubRepo.openIssuesCount)
                binding.stars.text = getString(R.string.stars, state.githubRepo.stargazersCount)
                binding.watchers.text = getString(R.string.watchers, state.githubRepo.watchersCount)
            }
        }
    }

    private fun showRepoInfo() = viewModel.showRepoInfo(fragmentArgs.repoName, fragmentArgs.repoOwner)
}