package com.example.githubsearch.features.search.domain.usecase

import com.example.githubsearch.UnitTest
import com.example.githubsearch.core.functional.Either
import com.example.githubsearch.features.search.domain.repository.ISearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class SearchUseCaseTest : UnitTest() {

    private lateinit var searchUseCase: SearchUseCase

    @MockK
    private lateinit var searchRepository: ISearchRepository

    @Before
    fun setUp() {
        searchUseCase = SearchUseCase(searchRepository)
    }

    @Test
    fun `should call login`() = runBlockingTest {
        val params = "Doe"
        coEvery {
            searchRepository.login(params)
        } returns flow {
            emit(Either.Right(true))
        }
        searchUseCase.run(params)
        coVerify(exactly = 1) {
            searchRepository.login(params)
        }
    }
}