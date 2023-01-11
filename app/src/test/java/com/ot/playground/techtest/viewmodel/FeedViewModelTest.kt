package com.ot.playground.techtest.viewmodel

import androidx.lifecycle.Observer
import com.ot.playground.techtest.CoroutineTestRule
import com.ot.playground.techtest.InstantExecutorExtension
import com.ot.playground.techtest.model.Result
import com.ot.playground.techtest.model.apidata.GithubRepoItem
import com.ot.playground.techtest.observeWithMock
import com.ot.playground.techtest.repository.GithubRepoRepository
import com.ot.playground.techtest.viewmodel.FeedViewModel.ViewEvent
import com.ot.playground.techtest.viewmodel.FeedViewModel.ViewEvent.Error
import com.ot.playground.techtest.viewmodel.FeedViewModel.ViewState
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import retrofit2.Response

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class FeedViewModelTest {
    private lateinit var sut: FeedViewModel
    private val repository = mockk<GithubRepoRepository>(relaxed = true)
    private lateinit var viewStateObserver: Observer<ViewState>
    private lateinit var viewEventObserver: Observer<ViewEvent>

    @JvmField
    @RegisterExtension
    val coroutineTestRule = CoroutineTestRule()


    @BeforeEach
    fun setup() {
        sut = FeedViewModel(repository, coroutineTestRule.testDispatcherProvider)
        viewStateObserver = sut.viewState.observeWithMock()
        viewEventObserver = sut.viewEvent.observeWithMock()
    }


    @Test
    fun `given getRepositories returns repositories then emit repositories`() {
        val repos: List<GithubRepoItem> = listOf()
        coroutineTestRule.runBlockingTest {
            coEvery { repository.getRepositories() } returns Result.Success(mockk())
            sut.getGithubRepos()
            val expectedResult = ViewState(repos)
            verify { viewStateObserver.onChanged(expectedResult) }
        }
    }

    @Test
    fun `given getRepositories returns error then emit error`() {
        coroutineTestRule.runBlockingTest {
            coEvery { repository.getRepositories() } returns Result.Error(mockk())
            sut.getGithubRepos()
            val expectedResult = Error
            verify { viewEventObserver.onChanged(expectedResult) }
        }
    }

}