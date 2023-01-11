package com.ot.playground.techtest.model.localdata

import android.util.Log
import com.ot.playground.techtest.model.apidata.GithubRepo

data class GithubRepoItem(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val owner: String = "",
    val watchers: Int
)

fun GithubRepo.map(): List<GithubRepoItem> {

    return this.map { repoItem ->
        GithubRepoItem(repoItem.id, repoItem.name, repoItem.description ?: "", repoItem.owner.login, repoItem.watchersCount) }
}
