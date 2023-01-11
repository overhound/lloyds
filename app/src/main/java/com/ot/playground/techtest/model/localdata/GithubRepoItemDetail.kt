package com.ot.playground.techtest.model.localdata

import com.ot.playground.techtest.model.apidata.GithubRepoItem

data class GithubRepoItemDetail(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val issues: Int,
    val isPrivate: Boolean,
    val watchers: Int,
    val stars: Int,
    val topics: List<String> = emptyList()
)
fun GithubRepoItem.map() = GithubRepoItemDetail(id, fullName, description ?: "", openIssuesCount, private, watchersCount, stargazersCount, topics)
