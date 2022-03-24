package com.example.githubsearch.features.result.domain.mappers

import com.example.githubsearch.features.result.data.remote.GithubResponse
import com.example.githubsearch.features.result.domain.model.SearchResult

fun GithubResponse.toDomainObjects(): List<SearchResult>{

    return this.users.map { it.toDomainObject() }
}