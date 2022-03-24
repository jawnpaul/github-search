package com.example.githubsearch.features.search.data.repository

import com.example.githubsearch.core.exception.Failure
import com.example.githubsearch.core.functional.Either
import com.example.githubsearch.features.search.domain.repository.ISearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepository @Inject constructor() : ISearchRepository {
    override suspend fun login(params: String): Flow<Either<Failure, Boolean>> = flow {
        // This is to create login constraints

        if (params.length > 2) {
            emit(Either.Right(true))
        } else {
            emit(Either.Left(Failure.UnAuthorizedError))
        }
    }
}
