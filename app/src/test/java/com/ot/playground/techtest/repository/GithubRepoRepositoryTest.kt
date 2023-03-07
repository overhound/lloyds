package com.ot.playground.techtest.repository

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.ot.playground.techtest.CoroutineTestRule
import com.ot.playground.techtest.InstantExecutorExtension
import com.ot.playground.techtest.model.GithubService
import com.ot.playground.techtest.model.Result
import com.ot.playground.techtest.model.Result.Error
import com.ot.playground.techtest.model.apidata.GithubRepoItem
import com.ot.playground.techtest.repository.GithubRepoRepository.GithubRepoRepositoryImpl
import io.mockk.coEvery
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import retrofit2.Response
import kotlin.test.assertEquals


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class GithubRepoRepositoryTest {
    private lateinit var sut: GithubRepoRepository
    private val service = spyk<GithubService>()

    @JvmField
    @RegisterExtension
    val coroutineTestRule = CoroutineTestRule()

    @BeforeEach
    fun setup() {
        sut = GithubRepoRepositoryImpl(service)
    }

    @Test
    fun `given publicRepositories returns repositories return data`() =
        coroutineTestRule.coRunBlockingTest {
            val organisation = ""
            val serviceResponse = Response.success(listOf<GithubRepoItem>())
            val expected = Result.Success(serviceResponse.body()!!)

            coEvery { service.publicRepositories(organisation) } returns serviceResponse
            val result: Result<List<GithubRepoItem>> = sut.getRepositories(organisation)
            assertEquals(expected = expected, actual = result)
        }

    @Test
    fun `given publicRepositories returns error return error`() =
        coroutineTestRule.coRunBlockingTest {
            val organisation = ""
            val mediaType = "application/json".toMediaTypeOrNull()
            val gsonBuilder = GsonBuilder().create()
            val jsonString = "{\"message\":\"Not Found\"}"
            val jsonElement = gsonBuilder.fromJson(jsonString, JsonElement::class.java)

            val serviceResponse = Response.error<List<GithubRepoItem>>(
                404, jsonString.toResponseBody(mediaType)
            )
            coEvery { service.publicRepositories(organisation) } returns serviceResponse
            val expected = Error(RuntimeException(jsonElement.asJsonObject.get("message").asString))

            val result = sut.getRepositories(organisation) as? Error
            assertEquals(expected = expected.exception.message, actual = result?.exception?.message)
        }
}
