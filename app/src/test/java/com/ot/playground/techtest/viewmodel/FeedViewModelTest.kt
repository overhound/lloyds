package com.ot.playground.techtest.viewmodel

import com.ot.playground.techtest.CoroutineTestRule
import com.ot.playground.techtest.InstantExecutorExtension
import com.ot.playground.techtest.model.Result
import com.ot.playground.techtest.model.apidata.GithubRepo
import com.ot.playground.techtest.model.apidata.GithubRepoItem
import com.ot.playground.techtest.repository.GithubRepoRepository
import com.ot.playground.techtest.viewmodel.FeedViewModel.ViewEvent
import com.ot.playground.techtest.viewmodel.FeedViewModel.ViewState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class FeedViewModelTest {
    @JvmField
    @RegisterExtension
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var sut: FeedViewModel
    private val repository = mockk<GithubRepoRepository>(relaxed = true)
    private lateinit var initialState: ViewState


    @BeforeEach
    fun setup() {
        sut = FeedViewModel(repository, coroutineTestRule.testDispatcherProvider)
        initialState = sut.viewState.value
    }

    @Test
    fun `given viewModel is initialized viewEvent is Initial`() {
        assertEquals(initialState.viewEvent, ViewEvent.Initial)
    }


    @Test
    fun `given errorMessageShown viewEvent is None`() {
        coroutineTestRule.coRunBlockingTest {
            sut.errorMessageShown()
            assertEquals(ViewEvent.None, sut.viewState.drop(1).first().viewEvent)
        }
    }

    @Test
    fun `given navigatedToDetailScreen viewEvent is None`() {
        coroutineTestRule.coRunBlockingTest {
            sut.navigatedToDetailScreen()
            assertEquals(ViewEvent.None, sut.viewState.drop(1).first().viewEvent)
        }
    }


    @Test
    fun `given onClickRepository viewEvent is None`() {
        val id = 0
        val mockedRepoResult: List<GithubRepoItem> = listOf(GithubRepoItem(id = 3), GithubRepoItem(id = 2), GithubRepoItem(id = 332))
        coroutineTestRule.coRunBlockingTest {
            coEvery { repository.getRepositories(any()) } answers { Result.Success(mockedRepoResult) }
            sut.onClickRepository(id)
            assertEquals(ViewEvent.None, sut.viewState.drop(1).first().viewEvent)
        }
    }

    @Test
    fun `given getGithubRepos is viewEvent emit repositories and viewEvent is None`() {
        val mockedRepoResult = GithubRepo()
        coroutineTestRule.coRunBlockingTest {
            coEvery { repository.getRepositories(any()) } returns Result.Success(mockedRepoResult)
            sut.getGithubRepos()
            assertEquals(ViewEvent.None, sut.viewState.drop(1).first().viewEvent)
        }
    }

    @Test
    fun `given getRepositories returns error then emit error`() {
        val errorMessage = "message"
        coroutineTestRule.coRunBlockingTest {
            coEvery { repository.getRepositories(any()) } returns Result.Error(Exception(errorMessage))
            sut.getGithubRepos()
            assertEquals(ViewEvent.Error(errorMessage), sut.viewState.drop(1).first().viewEvent)
        }
    }
}