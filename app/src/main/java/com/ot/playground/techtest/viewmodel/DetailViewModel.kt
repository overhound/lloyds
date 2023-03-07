package com.ot.playground.techtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ot.playground.techtest.model.Result.Error
import com.ot.playground.techtest.model.Result.Success
import com.ot.playground.techtest.model.apidata.GithubRepoItem
import com.ot.playground.techtest.repository.GithubRepoRepository
import com.ot.playground.techtest.utils.coroutines.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val repository: GithubRepoRepository, private val dispatchers: DispatcherProvider) : ViewModel() {
    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState(viewEvent = ViewEvent.Initial))
    internal val viewState: StateFlow<ViewState> = _viewState

    fun showRepoInfo(repoName: String, repoOwner: String) {
        viewModelScope.launch(dispatchers.ui()) {
            when (val result = withContext(dispatchers.io()) { repository.getRepository(repoName, repoOwner) }) {
                is Success -> _viewState.updateState(result.data)
                is Error -> result.exception.message?.let { error ->
                    _viewState.emitEvent(ViewEvent.Error(error))
                }
            }
        }
    }

    fun errorMessageShown() {
        _viewState.emitEvent(ViewEvent.None)
    }

    private fun MutableStateFlow<ViewState>.emitEvent(viewEvent: ViewEvent) {
        viewModelScope.launch(dispatchers.ui()) {
            update { state -> (state.copy(viewEvent = viewEvent)) }
        }
    }

    private fun MutableStateFlow<ViewState>.updateState(githubRepo: GithubRepoItem) {
        viewModelScope.launch(dispatchers.ui()) {
            update { state -> (state.copy(githubRepo = githubRepo)) }
        }
    }

    internal sealed class ViewEvent {
        object Initial : ViewEvent()
        data class Error(val message: String) : ViewEvent()
        object None : ViewEvent()
    }

    internal data class ViewState(
        val githubRepo: GithubRepoItem = GithubRepoItem(),
        val viewEvent: ViewEvent = ViewEvent.None
    )
}