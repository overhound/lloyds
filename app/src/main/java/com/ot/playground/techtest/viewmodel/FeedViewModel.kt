package com.ot.playground.techtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ot.playground.techtest.model.Result
import com.ot.playground.techtest.model.apidata.GithubRepoItem
import com.ot.playground.techtest.repository.GithubRepoRepository
import com.ot.playground.techtest.utils.SingleLiveEvent
import com.ot.playground.techtest.utils.coroutines.DispatcherProvider
import kotlinx.coroutines.launch

class FeedViewModel(private val repository: GithubRepoRepository, private val dispatchers: DispatcherProvider) : ViewModel() {
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData(ViewState())
    private val _viewEvent: SingleLiveEvent<ViewEvent> = SingleLiveEvent()
    val viewState: LiveData<ViewState> = _viewState
    val viewEvent: LiveData<ViewEvent> = _viewEvent



    fun getGithubRepos() {
        viewModelScope.launch(dispatchers.io()) {
            when (val result = repository.getRepositories()) {
                is Result.Success -> _viewState.postValue(ViewState(result.data))
                is Result.Error -> _viewEvent.postValue(ViewEvent.Error)
            }
        }
    }


    fun navigateToDetailScreen(id: Int) {
        _viewState.value?.let {
            val repo = it.githubRepos[id]
            _viewEvent.postValue(ViewEvent.NavigateToDetailScreen(repo.name, repo.owner.login))
        }
    }

    sealed class ViewEvent {
        object Error : ViewEvent()
        data class NavigateToDetailScreen(val repoName: String, val repoOwner: String) : ViewEvent()
    }

    data class ViewState(
        val githubRepos: List<GithubRepoItem> = mutableListOf()
    )
}