package com.ot.playground.techtest.viewmodel

import com.ot.playground.techtest.CoroutineTestRule
import com.ot.playground.techtest.InstantExecutorExtension
import com.ot.playground.techtest.model.Result
import com.ot.playground.techtest.model.apidata.GithubRepoItem
import com.ot.playground.techtest.repository.GithubRepoRepository
import com.ot.playground.techtest.viewmodel.DetailViewModel.*
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
class DetailViewModelTest {
    @JvmField
    @RegisterExtension
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var sut: DetailViewModel
    private lateinit var initialState: ViewState
    private val repository = mockk<GithubRepoRepository>(relaxed = true)


    @BeforeEach
    fun setup() {
        sut = DetailViewModel(repository, coroutineTestRule.testDispatcherProvider)
        initialState = sut.viewState.value
    }


    @Test
    fun `given viewModel is initialized viewEvent is Initial`() {
        assertEquals(initialState.viewEvent, ViewEvent.Initial)
    }

    @Test
    fun `given errorMessageShown viewEvent is None`() = coroutineTestRule.coRunBlockingTest {
        sut.errorMessageShown()
        assertEquals(ViewEvent.None, sut.viewState.drop(1).first().viewEvent)
    }

    @Test
    fun `given getRepository returns a repository then emit repository`() {
        val repo = mockGetRepoArgs()
        val mockRepoItem = GithubRepoItem()
        coroutineTestRule.coRunBlockingTest {
            coEvery { repository.getRepository(repo.first, repo.second) } returns Result.Success(mockRepoItem)
            sut.showRepoInfo(repo.first, repo.second)
            val expectedResult = ViewState(mockRepoItem, viewEvent = initialState.viewEvent)
            assertEquals(expectedResult, sut.viewState.drop(1).first())
        }
    }

    @Test
    fun `given getRepository returns error then emit error`() {
        val repo = mockGetRepoArgs()
        val error = Result.Error(mockk(relaxed = true))
        coroutineTestRule.coRunBlockingTest {
            coEvery { repository.getRepository(repo.first, repo.second) } returns error
            sut.showRepoInfo(repo.first, repo.second)
            val expectedResult = ViewState(githubRepo = initialState.githubRepo, viewEvent = ViewEvent.Error(error.exception.message.toString()))
            assertEquals(expectedResult, sut.viewState.drop(1).first())
        }
    }

    private fun mockGetRepoArgs() = Pair("", "")
}