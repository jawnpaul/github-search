package com.example.githubsearch.core.di

import com.example.githubsearch.features.result.data.repository.ResultRepository
import com.example.githubsearch.features.result.domain.repository.IResultRepository
import com.example.githubsearch.features.search.data.repository.SearchRepository
import com.example.githubsearch.features.search.domain.repository.ISearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Binds
    abstract fun bindsSearchRepository(repo: SearchRepository): ISearchRepository

    @Binds
    abstract fun bindsResultRepository(repo: ResultRepository): IResultRepository
}
