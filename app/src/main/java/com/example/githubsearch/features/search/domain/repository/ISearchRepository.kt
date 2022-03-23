package com.example.githubsearch.features.search.domain.repository

import com.example.githubsearch.core.exception.Failure
import com.example.githubsearch.core.functional.Either
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {

    suspend fun login(params: String): Flow<Either<Failure, Boolean>>
}