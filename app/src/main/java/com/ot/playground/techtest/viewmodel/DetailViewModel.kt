package com.ot.playground.techtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ot.playground.techtest.model.Result.Error
import com.ot.playground.techtest.model.Result.Success
import com.ot.playground.techtest.model.apidata.GithubRepoItem
import com.ot.playground.techtest.repository.GithubRepoRepository
import com.ot.playground.techtest.utils.SingleLiveEvent
import com.ot.playground.techtest.utils.coroutines.DispatcherProvider
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: GithubRepoRepository, private val dispatchers: DispatcherProvider) : ViewModel() {
    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()
    private val _viewEvent: SingleLiveEvent<ViewEvent> = SingleLiveEvent()

    val viewState: LiveData<ViewState> = _viewState
    val viewEvent: LiveData<ViewEvent> = _viewEvent

    fun showRepoInfo(repoName: String, repoOwner: String) {
        viewModelScope.launch(dispatchers.io()) {
            when (val result = repository.getRepository(repoName, repoOwner)) {
                is Success -> _viewState.postValue(ViewState(result.data))
                is Error -> _viewEvent.postValue(ViewEvent.Error)
            }
        }
    }


    sealed class ViewEvent {
        object Error : ViewEvent()
    }

    data class ViewState(
        val githubRepo: GithubRepoItem
    )
}