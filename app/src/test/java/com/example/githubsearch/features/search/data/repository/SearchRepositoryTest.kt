package com.example.githubsearch.features.search.data.repository

import com.example.githubsearch.UnitTest
import com.example.githubsearch.core.exception.Failure
import com.example.githubsearch.core.functional.Either
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchRepositoryTest : UnitTest() {

    private lateinit var searchRepository: SearchRepository

    @Before
    fun setUp() {
        searchRepository = SearchRepository()
    }

    @Test
    fun `login attempt with less than three characters should return unauthorized `() =
        runBlockingTest {
            val query = "aa"

            val result = searchRepository.login(query)
            result.collect { a ->
                assertEquals(a.isLeft, true)
                assertEquals(a, Either.Left(Failure.UnAuthorizedError))
            }
        }

    @Test
    fun `login attempt with at least three characters should succeed `() =
        runBlockingTest {
            val query = "aaa"

            val result = searchRepository.login(query)
            result.collect { a ->
                assertEquals(a.isRight, true)
                assertEquals(a, Either.Right(true))
            }
        }
}
