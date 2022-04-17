package com.example.githubsearch.features.result.domain.mappers

import com.example.githubsearch.UnitTest
import com.example.githubsearch.features.result.data.remote.GithubResponse
import com.example.githubsearch.features.result.data.remote.UserResponse
import com.example.githubsearch.features.result.domain.model.SearchResult
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class JsonResponseToDomainKtTest : UnitTest() {

    @Test
    fun `githubResponse to domain should return empty list`() {
        val githubResponse = GithubResponse(
            totalCount = 1000,
            incomplete = false,
            users = emptyList()
        )
        assertThat(githubResponse.toDomainObjects()).isEmpty()
    }

    @Test
    fun `githubResponse to domain should return list`() {

        val userOne = SearchResult(
            profileUrl = "",
            name = "John",
            avatarUrl = "avatar url",
            type = "User",
        )

        val userTwo = SearchResult(
            profileUrl = "",
            name = "Doe",
            avatarUrl = "avatar url",
            type = "User",
        )

        val githubResponse = GithubResponse(
            totalCount = 1000,
            incomplete = false,
            users = listOf(
                UserResponse(
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
                ),
                UserResponse(
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
                )
            )
        )
        val expected = listOf(userOne, userTwo)
        assertThat(githubResponse.toDomainObjects()).isNotEmpty()
        assertThat(githubResponse.toDomainObjects()).isEqualTo(expected)
    }
}
