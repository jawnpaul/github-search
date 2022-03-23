package com.example.githubsearch.features.result.presentation.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearch.features.result.presentation.adapter.SearchResultAdapter
import com.example.githubsearch.features.result.presentation.model.SearchResultPresentation

@BindingAdapter("searchResults")
fun setSearchResultItems(listView: RecyclerView, list: List<SearchResultPresentation>?) {
    list?.let {
        (listView.adapter as SearchResultAdapter).submitList(list)
    }
}
