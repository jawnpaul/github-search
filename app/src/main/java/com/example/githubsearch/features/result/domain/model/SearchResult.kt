package com.example.githubsearch.features.result.domain.model

import com.example.githubsearch.features.result.presentation.model.SearchResultPresentation

data class SearchResult(
    val avatarUrl: String,
    val name: String,
    val type: String,
) {
    fun toPresentation() = SearchResultPresentation(avatarUrl, name, type)
}