package com.example.githubsearch.features.result.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubsearch.databinding.SingleResultItemBinding
import com.example.githubsearch.features.result.presentation.model.SearchResultPresentation

class SearchResultAdapter() :
    ListAdapter<SearchResultPresentation, SearchResultAdapter.ViewHolder>(SearchResultDiffCallback()) {

    class ViewHolder constructor(private val binding: SingleResultItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: SearchResultPresentation,
        ) {
            binding.nameTextView.text = item.name
            binding.typeTextView.text = item.type
            binding.avatarImageView.load(item.avatarUrl) {
                transformations(CircleCropTransformation())
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleResultItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class SearchResultDiffCallback : DiffUtil.ItemCallback<SearchResultPresentation>() {
    override fun areItemsTheSame(
        oldItem: SearchResultPresentation,
        newItem: SearchResultPresentation,
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: SearchResultPresentation,
        newItem: SearchResultPresentation,
    ): Boolean {
        return oldItem == newItem
    }
}
