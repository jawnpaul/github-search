package com.example.githubsearch.core.api

import retrofit2.http.GET

interface ApiService {

    @GET()
    suspend fun searchUser()
}