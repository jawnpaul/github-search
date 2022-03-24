package com.example.githubsearch.features.search.domain.usecase

import com.example.githubsearch.core.baseusecase.BaseUseCase
import com.example.githubsearch.core.exception.Failure
import com.example.githubsearch.core.functional.Either
import com.example.githubsearch.features.search.domain.repository.ISearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val repository: ISearchRepository) :
    BaseUseCase<String, Boolean>() {
    override suspend fun run(params: String): Flow<Either<Failure, Boolean>> {
        return repository.login(params)
    }
}
