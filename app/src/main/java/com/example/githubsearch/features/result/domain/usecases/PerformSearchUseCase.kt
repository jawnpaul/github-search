package com.example.githubsearch.features.result.domain.usecases

import com.example.githubsearch.core.baseusecase.BaseUseCase
import com.example.githubsearch.core.exception.Failure
import com.example.githubsearch.core.functional.Either
import com.example.githubsearch.features.result.data.model.SearchQuery
import com.example.githubsearch.features.result.domain.model.SearchResult
import com.example.githubsearch.features.result.domain.repository.IResultRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PerformSearchUseCase @Inject constructor(private val repository: IResultRepository) :
    BaseUseCase<SearchQuery, List<SearchResult>>() {
    override suspend fun run(params: SearchQuery): Flow<Either<Failure, List<SearchResult>>> {
        return repository.performSearch(params)
    }
}
