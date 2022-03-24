package com.example.githubsearch.features.result.presentation.mapper

import com.example.githubsearch.features.result.domain.model.SearchResult
import com.example.githubsearch.features.result.presentation.model.SearchResultPresentation

fun SearchResult.toPresentation(): SearchResultPresentation {

    return SearchResultPresentation(avatarUrl = this.avatarUrl, name = this.name, type = this.type)
}
