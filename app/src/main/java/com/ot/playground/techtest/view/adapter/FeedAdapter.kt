package com.ot.playground.techtest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ot.playground.techtest.R
import com.ot.playground.techtest.databinding.ItemGithubRepoBinding
import com.ot.playground.techtest.model.apidata.GithubRepoItem

class FeedAdapter(val onClick: (position: Int) -> Unit) : ListAdapter<GithubRepoItem, FeedAdapter.ViewHolder>(FeedDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        val id = holder.adapterPosition
        holder.itemView.setOnClickListener { onClick(id) }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemGithubRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GithubRepoItem) {
            binding.title.text = item.name
            binding.description.text = item.description
            binding.watchers.text = binding.root.context.getString(R.string.watchers, item.watchersCount)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemGithubRepoBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}