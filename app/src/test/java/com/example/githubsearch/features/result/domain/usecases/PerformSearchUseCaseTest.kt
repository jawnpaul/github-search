package com.example.githubsearch.features.result.domain.usecases

import com.example.githubsearch.UnitTest
import com.example.githubsearch.core.functional.Either
import com.example.githubsearch.features.result.data.model.SearchQuery
import com.example.githubsearch.features.result.domain.model.SearchResult
import com.example.githubsearch.features.result.domain.repository.IResultRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PerformSearchUseCaseTest : UnitTest() {

    private lateinit var performSearchUseCase: PerformSearchUseCase

    @MockK
    private lateinit var repository: IResultRepository

    @Before
    fun setUp() {
        performSearchUseCase = PerformSearchUseCase(repository)
    }

    @Test
    fun `should call performSearch`() = runBlocking {
        val query = SearchQuery(query = "John", page = 1)
        coEvery {
            repository.performSearch(query)
        } returns flow {
            emit(
                Either.Right(
                    listOf(
                        SearchResult(
                            avatarUrl = "url",
                            name = "name",
                            type = "type"
                        )
                    )
                )
            )
        }
        performSearchUseCase.run(query)
        coVerify(exactly = 1) {
            repository.performSearch(query)
        }
    }
}
