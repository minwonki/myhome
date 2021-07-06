package com.example.myhome.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.repository.MyHomeRepository
import com.example.network.helper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo : MyHomeRepository
) : ViewModel() {

    var state by mutableStateOf<HomeViewState>(HomeViewState.Empty)
        private set

    init {
        getHome()
    }

    fun getHome() {
        viewModelScope.launch {
            repo.homes().collect { result ->
                when (result) {
                    is ResultWrapper.GenericError -> {
                        state = HomeViewState.Alert(msg = "${result.message}")
                    }
                    ResultWrapper.Loading -> { state = HomeViewState.Loading }
                    is ResultWrapper.NetworkError -> {
                        state = HomeViewState.Alert(msg = "${result.message}")
                    }
                    is ResultWrapper.Success -> {
                        state = if (result.value.Ok) {
                            HomeViewState.Success(
                                cards = result.value.popularCards,
                                users = result.value.popularUsers
                            )
                        } else {
                            HomeViewState.Alert(msg = "${result.value.errorMsg}")
                        }

                    }
                }
            }
        }
    }
}

