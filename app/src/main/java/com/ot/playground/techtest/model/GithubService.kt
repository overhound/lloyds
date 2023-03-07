package com.ot.playground.techtest.model

import com.ot.playground.techtest.model.apidata.GithubRepoItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GithubService {
    @GET("orgs/{organisation}/repos")
    suspend fun publicRepositories(@Path("organisation") organisation: String): Response<List<GithubRepoItem>>


    @Headers("Accept: application/vnd.github.v3+json")
    @GET("repos/{owner}/{repository}")
    suspend fun publicRepository(@Path("repository") repository: String, @Path("owner") owner: String): Response<GithubRepoItem>
}