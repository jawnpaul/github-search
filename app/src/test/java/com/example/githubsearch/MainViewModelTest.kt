package com.example.githubsearch

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubsearch.core.exception.Failure
import com.example.githubsearch.core.functional.Either
import com.example.githubsearch.features.result.data.model.SearchQuery
import com.example.githubsearch.features.result.domain.model.SearchResult
import com.example.githubsearch.features.result.domain.usecases.PerformSearchUseCase
import com.example.githubsearch.features.result.presentation.mapper.toPresentation
import com.example.githubsearch.features.search.domain.usecase.SearchUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P], manifest = Config.NONE)
class MainViewModelTest : UnitTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var searchUseCase: SearchUseCase

    @MockK
    private lateinit var performSearchUseCase: PerformSearchUseCase

    private lateinit var mainViewModel: MainViewModel

    private lateinit var loginParams: String
    private lateinit var query: String
    private var page: Int = 1
    private lateinit var searchQuery: SearchQuery
    private lateinit var result: List<SearchResult>

    @Before
    fun setUp() {
        loginParams = "John"
        query = "John"
        page = 1
        searchQuery = SearchQuery(query, page)

        result = listOf(
            SearchResult(profileUrl = "", avatarUrl = "", name = "", type = "")
        )

        mainViewModel = MainViewModel(searchUseCase, performSearchUseCase)
    }

    @Test
    fun `login should show unauthorized error when error occurs`() {
        every { searchUseCase(any(), loginParams, any()) }.answers {
            thirdArg<(Either<Failure, Boolean>) -> Unit>()(Either.Left(Failure.UnAuthorizedError))
        }
        mainViewModel.search(loginParams)
        val res = mainViewModel.loginView.getOrAwaitValueTest()

        assertThat(res.error).isNotNull()
        assertThat(res.response).isFalse()
    }

    @Test
    fun `login should return true on success`() {
        every { searchUseCase(any(), loginParams, any()) }.answers {
            thirdArg<(Either<Failure, Boolean>) -> Unit>()(Either.Right(true))
        }
        mainViewModel.search(loginParams)

        val res = mainViewModel.loginView.getOrAwaitValueTest()
        assertThat(res.error).isNull()
        assertThat(res.response).isTrue()
    }

    @Test
    fun `performSearch should show error when an error occurs`() {
        every { performSearchUseCase(any(), searchQuery, any()) }.answers {
            thirdArg<(Either<Failure, List<SearchResult>>) -> Unit>()(Either.Left(Failure.ServerError))
        }
        mainViewModel.performSearch(query, page)

        val res = mainViewModel.searchResult.getOrAwaitValueTest()
        assertThat(res.error).isNotNull()
        assertThat(res.loading).isFalse()
        assertThat(res.result).isNull()
    }

    @Test
    fun `performSearch should return result list on success`() {
        every { performSearchUseCase(any(), searchQuery, any()) }.answers {
            thirdArg<(Either<Failure, List<SearchResult>>) -> Unit>()(Either.Right(result))
        }
        mainViewModel.performSearch(query, page)

        val res = mainViewModel.searchResult.getOrAwaitValueTest()
        assertThat(res.error).isNull()
        assertThat(res.loading).isFalse()
        assertThat(res.result).isEqualTo(result.map { it.toPresentation() })
    }
}
