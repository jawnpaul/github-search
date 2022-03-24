package com.example.githubsearch.features.result.data.repository

import com.example.githubsearch.UnitTest
import com.example.githubsearch.core.api.ApiService
import com.example.githubsearch.core.exception.Failure
import com.example.githubsearch.core.functional.Either
import com.example.githubsearch.features.result.data.model.SearchQuery
import com.example.githubsearch.features.result.data.remote.GithubResponse
import com.example.githubsearch.features.result.data.remote.UserResponse
import com.example.githubsearch.features.result.domain.mappers.toDomainObjects
import com.google.common.truth.ExpectFailure.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ResultRepositoryTest : UnitTest() {
    private lateinit var searchQuery: String
    private var perPage: Int = 0
    private var page: Int = 1
    private lateinit var repository: ResultRepository


    @MockK
    private lateinit var apiService: ApiService

    @MockK
    private lateinit var githubResponse: Response<GithubResponse>

    @Before
    fun setUp() {
        searchQuery = "John"
        perPage = 9
        page = 2
        repository = ResultRepository(apiService)
    }

    @Test
    fun `searchUser with null responseBody should return data error`() = runBlockingTest {
        every { githubResponse.body() } returns null
        every { githubResponse.isSuccessful } returns true
        coEvery {
            apiService.searchUser(searchQuery,
                perPage,
                page)
        } returns githubResponse

        val response = repository.performSearch(SearchQuery(searchQuery, page))

        response.collect { a ->
            assertEquals(a, Either.Left(Failure.DataError))
        }
    }

    @Test
    fun `searchUser should return server error when response is not successful`() =
        runBlockingTest {
            every { githubResponse.isSuccessful } returns false
            coEvery {
                apiService.searchUser(searchQuery,
                    perPage,
                    page)
            } returns githubResponse

            val response = repository.performSearch(SearchQuery(searchQuery, page))
            response.collect { a ->
                assertEquals(a, Either.Left(Failure.ServerError))
            }
        }

    @Test
    fun `searchUser should return search result list`() = runBlockingTest {
        val localResponse = GithubResponse(
            totalCount = 1000,
            incomplete = false,
            users = listOf(UserResponse(
                name = "John",
                id = 1,
                nodeId = "sd",
                avatarUrl = "avatar url",
                gravatar = "gravatar",
                url = "url",
                htmlUrl = "",
                followersUrl = "",
                followingUrl = "",
                gistUrl = "",
                starredUrl = "",
                subscriptionUrl = "",
                organizationUrl = "",
                repoUrl = "",
                eventUrl = "",
                receivedEventUrl = "",
                type = "User",
                siteAdmin = false,
                score = 1F
            ), UserResponse(
                name = "Doe",
                id = 2,
                nodeId = "sd",
                avatarUrl = "avatar url",
                gravatar = "gravatar",
                url = "url",
                htmlUrl = "",
                followersUrl = "",
                followingUrl = "",
                gistUrl = "",
                starredUrl = "",
                subscriptionUrl = "",
                organizationUrl = "",
                repoUrl = "",
                eventUrl = "",
                receivedEventUrl = "",
                type = "User",
                siteAdmin = false,
                score = 1F
            ))
        )

        every { githubResponse.isSuccessful } returns true
        every { githubResponse.body() } returns localResponse
        coEvery {
            apiService.searchUser(searchQuery,
                perPage,
                page)
        } returns githubResponse

        val result = repository.performSearch(SearchQuery(searchQuery, page))
        result.collect { a ->
            assertEquals(a, Either.Right(localResponse.toDomainObjects()))
        }
    }
}