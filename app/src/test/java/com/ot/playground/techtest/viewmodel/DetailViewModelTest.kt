package com.ot.playground.techtest.viewmodel

import androidx.lifecycle.Observer
import com.ot.playground.techtest.CoroutineTestRule
import com.ot.playground.techtest.InstantExecutorExtension
import com.ot.playground.techtest.model.Result
import com.ot.playground.techtest.model.apidata.GithubRepoItem
import com.ot.playground.techtest.observeWithMock
import com.ot.playground.techtest.repository.GithubRepoRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class DetailViewModelTest {
    private lateinit var sut: DetailViewModel
    private val repository = mockk<GithubRepoRepository>(relaxed = true)
    private lateinit var viewStateObserver: Observer<DetailViewModel.ViewState>
    private lateinit var viewEventObserver: Observer<DetailViewModel.ViewEvent>

    @JvmField
    @RegisterExtension
    val coroutineTestRule = CoroutineTestRule()


    @BeforeEach
    fun setup() {
        sut = DetailViewModel(repository, coroutineTestRule.testDispatcherProvider)
        viewStateObserver = sut.viewState.observeWithMock()
        viewEventObserver = sut.viewEvent.observeWithMock()
    }

    @Test
    fun `given getRepository returns a repository then emit repository`() {
        val repo = mockGetRepoArgs()
        val mockRepoItem = mockk<GithubRepoItem>()
        coroutineTestRule.runBlockingTest {
            coEvery { repository.getRepository(repo.first, repo.second) } returns Result.Success(mockRepoItem)
            sut.showRepoInfo(repo.first, repo.second)
            val expectedResult = DetailViewModel.ViewState(mockRepoItem)
            verify { viewStateObserver.onChanged(expectedResult) }
        }
    }

    @Test
    fun `given getRepository returns error then emit error`() {
        val repo = mockGetRepoArgs()
        coroutineTestRule.runBlockingTest {
            coEvery { repository.getRepository(repo.first, repo.second) } returns Result.Error(mockk())
            sut.showRepoInfo(repo.first, repo.second)
            val expectedResult = DetailViewModel.ViewEvent.Error
            verify { viewEventObserver.onChanged(expectedResult) }
        }
    }

    private fun mockGetRepoArgs() = Pair("", "")
}