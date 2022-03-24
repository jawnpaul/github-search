package com.example.githubsearch.features.result.data.repository

import com.example.githubsearch.core.api.ApiService
import com.example.githubsearch.core.exception.Failure
import com.example.githubsearch.core.functional.Either
import com.example.githubsearch.features.result.data.model.SearchQuery
import com.example.githubsearch.features.result.domain.mappers.toDomainObjects
import com.example.githubsearch.features.result.domain.model.SearchResult
import com.example.githubsearch.features.result.domain.repository.IResultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class ResultRepository @Inject constructor(private val apiService: ApiService) : IResultRepository {
    override suspend fun performSearch(query: SearchQuery): Flow<Either<Failure, List<SearchResult>>> =
        flow {
            try {
                val res =
                    apiService.searchUser(perPage = 9, page = query.page, params = query.query)
                emit(
                    when (res.isSuccessful) {
                        true -> {
                            res.body()?.let { it ->
                                Either.Right(it.toDomainObjects())
                            } ?: Either.Left(Failure.DataError)
                        }
                        false -> {
                            Either.Left(Failure.ServerError)
                        }
                    }
                )
            } catch (e: Exception) {
                emit(Either.Left(Failure.ServerError))
                Timber.e(e.stackTraceToString())
            }
        }
}
