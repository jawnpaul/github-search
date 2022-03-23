package com.example.githubsearch.features.result.domain.repository

import com.example.githubsearch.core.exception.Failure
import com.example.githubsearch.core.functional.Either
import com.example.githubsearch.features.result.data.model.SearchQuery
import com.example.githubsearch.features.result.domain.model.SearchResult
import kotlinx.coroutines.flow.Flow

interface IResultRepository {

    suspend fun performSearch(query: SearchQuery): Flow<Either<Failure, List<SearchResult>>>
}