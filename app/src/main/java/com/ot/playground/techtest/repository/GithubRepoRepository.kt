package com.ot.playground.techtest.repository

import com.ot.playground.techtest.model.GithubService
import com.ot.playground.techtest.model.Result
import com.ot.playground.techtest.model.apiCall
import com.ot.playground.techtest.model.apidata.GithubRepoItem

interface GithubRepoRepository {

    suspend fun getRepositories(organisationName: String = "firebase"): Result<List<GithubRepoItem>>
    suspend fun getRepository(repoName: String, repoOwner: String): Result<GithubRepoItem>

    class GithubRepoRepositoryImpl(private val githubService: GithubService) : GithubRepoRepository {

        override suspend fun getRepositories(organisationName: String): Result<List<GithubRepoItem>> = apiCall({ githubService.publicRepositories(organisationName) })
        override suspend fun getRepository(repoName: String, repoOwner: String): Result<GithubRepoItem> = apiCall({ githubService.publicRepository(repoName, repoOwner) })
    }
}
