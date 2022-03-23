package com.example.githubsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsearch.core.exception.Failure
import com.example.githubsearch.features.search.domain.usecase.SearchUseCase
import com.example.githubsearch.features.search.presentation.state.LoginView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : ViewModel() {
    private val job = Job()

    private val _loginView = MutableLiveData<LoginView>()
    val loginView: LiveData<LoginView> get() = _loginView

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

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}