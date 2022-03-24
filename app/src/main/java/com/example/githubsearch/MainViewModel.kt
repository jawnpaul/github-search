package com.example.githubsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsearch.core.exception.Failure
import com.example.githubsearch.features.result.data.model.SearchQuery
import com.example.githubsearch.features.result.domain.model.SearchResult
import com.example.githubsearch.features.result.domain.usecases.PerformSearchUseCase
import com.example.githubsearch.features.result.presentation.mapper.toPresentation
import com.example.githubsearch.features.result.presentation.state.ResultView
import com.example.githubsearch.features.search.domain.usecase.SearchUseCase
import com.example.githubsearch.features.search.presentation.state.LoginView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    private val performSearchUseCase: PerformSearchUseCase,
) : ViewModel() {
    private val job = Job()

    private val _loginView = MutableLiveData<LoginView>()
    val loginView: LiveData<LoginView> get() = _loginView

    private val _searchResult = MutableLiveData<ResultView>()
    val searchResult: LiveData<ResultView> get() = _searchResult

    private val _nextPage = MutableLiveData<Int?>()
    val nextPage: LiveData<Int?> get() = _nextPage

    fun search(params: String) {
        searchUseCase(job, params) {
            it.fold(
                ::handleLoginFailure,
                ::handleLoginSuccess
            )
        }
    }

    private fun handleLoginSuccess(result: Boolean) {
        _loginView.value = LoginView(response = result)
    }

    private fun handleLoginFailure(failure: Failure) {
        when (failure) {
            is Failure.UnAuthorizedError -> {
                _loginView.value = LoginView(error = "Invalid login")
            }
            else -> {
                _loginView.value = LoginView(error = "Something went wrong.")
            }
        }
    }

    fun performSearch(query: String, page: Int) {
        _searchResult.value = ResultView(loading = true)
        val searchQuery = SearchQuery(query = query, page = page)

        performSearchUseCase(job, searchQuery) {
            it.fold(
                ::handleSearchFailure,
                ::handleSearchSuccess
            )
        }
    }

    private fun handleSearchFailure(failure: Failure) {
        _searchResult.value = ResultView(loading = false, error = "Something went wrong.")
        Timber.e(failure.toString())
    }

    private fun handleSearchSuccess(result: List<SearchResult>) {
        _searchResult.value =
            ResultView(loading = false, result = result.map { it.toPresentation() })
        Timber.d(result.size.toString())
    }

    fun setPage(page: Int) {
        _nextPage.value = page
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}