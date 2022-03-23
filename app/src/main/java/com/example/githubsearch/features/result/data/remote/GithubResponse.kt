package com.example.githubsearch.features.result.data.remote

import com.example.githubsearch.features.result.domain.model.SearchResult
import com.squareup.moshi.Json

data class GithubResponse(
    @field:Json(name = "total_count") val totalCount: String,
    @field:Json(name = "incomplete_results") val incomplete: Boolean,
    @field:Json(name = "items") val users: List<UserResponse>,
)

data class UserResponse(
    @field:Json(name = "login") val name: String,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "node_id") val nodeId: String,
    @field:Json(name = "avatar_url") val avatarUrl: String,
    @field:Json(name = "gravatar_id") val gravatar: String,
    @field:Json(name = "url") val url: String,
    @field:Json(name = "html_url") val htmlUrl: String,
    @field:Json(name = "followers_url") val followersUrl: String,
    @field:Json(name = "following_url") val followingUrl: String,
    @field:Json(name = "gists_url") val gistUrl: String,
    @field:Json(name = "starred_url") val starredUrl: String,
    @field:Json(name = "subscriptions_url") val subscriptionUrl: String,
    @field:Json(name = "organizations_url") val organizationUrl: String,
    @field:Json(name = "repos_url") val repoUrl: String,
    @field:Json(name = "events_url") val eventUrl: String,
    @field:Json(name = "received_events_url") val receivedEventUrl: String,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "site_admin") val siteAdmin: Boolean,
    @field:Json(name = "score") val score: Float,
) {
    fun toDomainObject() = SearchResult(avatarUrl = avatarUrl, name = name, type = type)
}
