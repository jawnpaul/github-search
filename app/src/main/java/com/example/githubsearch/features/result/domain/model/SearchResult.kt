package com.example.githubsearch.features.result.domain.model

data class SearchResult(
    val profileUrl: String,
    val avatarUrl: String,
    val name: String,
    val type: String,
)
