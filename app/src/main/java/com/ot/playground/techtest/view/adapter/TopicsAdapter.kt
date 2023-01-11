package com.ot.playground.techtest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ot.playground.techtest.databinding.LayoutTopicBinding

class TopicsAdapter(private val topics: MutableList<String>) : RecyclerView.Adapter<TopicsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = topics[position]
        holder.bind(item)
    }

    fun submitList(topics: List<String>) {
        this.topics.clear()
        this.topics.addAll(topics)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: LayoutTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.topic.text = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    LayoutTopicBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = topics.size
}