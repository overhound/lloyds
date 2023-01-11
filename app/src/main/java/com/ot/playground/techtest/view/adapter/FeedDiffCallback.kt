package com.ot.playground.techtest.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ot.playground.techtest.model.apidata.GithubRepoItem

class FeedDiffCallback : DiffUtil.ItemCallback<GithubRepoItem>() {
    override fun areItemsTheSame(oldItem: GithubRepoItem, newItem: GithubRepoItem): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: GithubRepoItem, newItem: GithubRepoItem): Boolean = oldItem == newItem
}