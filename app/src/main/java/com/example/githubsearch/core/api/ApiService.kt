package com.example.githubsearch.core.api

import com.example.githubsearch.features.result.data.remote.GithubResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") params: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): Response<GithubResponse>
}
