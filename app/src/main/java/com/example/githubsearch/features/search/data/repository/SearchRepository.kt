package com.example.githubsearch.features.search.data.repository

import com.example.githubsearch.core.exception.Failure
import com.example.githubsearch.core.functional.Either
import com.example.githubsearch.features.search.domain.repository.ISearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepository: ISearchRepository {
    override suspend fun login(params: String): Flow<Either<Failure, Boolean>> = flow {

    }
}