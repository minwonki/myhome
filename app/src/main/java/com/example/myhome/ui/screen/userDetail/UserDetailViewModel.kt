package com.example.myhome.ui.screen.userDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.repository.MyHomeRepository
import com.example.network.helper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: MyHomeRepository
) : ViewModel() {
    var userId: Int? = savedStateHandle.get("userId")
        private set

    var state =
        MutableStateFlow(AppState(viewState = UserDetailViewState.Empty, alert = Pair(false, "")))
        private set

    init {
        Timber.i("init : userId = $userId")
        userId?.let {
            userInfo(userId = it)
        }
    }

    fun closeAlert() {
        state.value = state.value.copy(alert = Pair(first = false, ""))
    }

    fun userInfo(userId: Int) {
        viewModelScope.launch {
            repo.userDetails(userId = userId).collect { result ->
                when (result) {
                    is ResultWrapper.GenericError -> {
                        state.value =
                            state.value.copy(alert = Pair(true, "${result.message}"))
                    }
                    ResultWrapper.Loading -> {
                        state.value =
                            state.value.copy(viewState = UserDetailViewState.Loading)
                    }
                    is ResultWrapper.NetworkError -> {
                        state.value =
                            state.value.copy(alert = Pair(true, "${result.message}"))
                    }
                    is ResultWrapper.Success -> {
                        if (result.value.Ok) {
                            state.value =
                                state.value.copy(viewState = UserDetailViewState.Success(user = result.value.user))
                        } else {
                            state.value =
                                state.value.copy(alert = Pair(true, "${result.value.errorMsg}"))
                        }
                    }
                }
            }
        }
    }


}

