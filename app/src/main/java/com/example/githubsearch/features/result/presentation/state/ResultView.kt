package com.example.githubsearch.features.result.presentation.state

import com.example.githubsearch.features.result.presentation.model.SearchResultPresentation

data class ResultView(
    val loading: Boolean? = null,
    val result: List<SearchResultPresentation>? = null,
    val error: String? = null,
)
