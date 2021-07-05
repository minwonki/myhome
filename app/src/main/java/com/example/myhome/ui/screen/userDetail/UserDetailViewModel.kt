package com.example.myhome.ui.screen.userDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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
class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo : MyHomeRepository
): ViewModel() {
    var userId : Int? = savedStateHandle.get("userId")
        private set

    var state by mutableStateOf<UserDetailViewState>(UserDetailViewState.Empty)
        private set

    init {
        Timber.i("init : userId = $userId")
        userId?.let {
            userInfo(userId = it)
        }
    }

    private fun userInfo(userId : Int) {
        viewModelScope.launch {
            repo.userDetails(userId = userId).collect { result ->
                when (result) {
                    is ResultWrapper.GenericError -> {
                        state = UserDetailViewState.Alert(msg = "${result.message}")
                    }
                    ResultWrapper.Loading -> {
                        state = UserDetailViewState.Loading
                    }
                    is ResultWrapper.NetworkError -> {
                        state = UserDetailViewState.Alert(msg = "${result.message}")
                    }
                    is ResultWrapper.Success -> {
                        state = if (result.value.Ok) {
                            UserDetailViewState.Success(user = result.value.user)
                        } else {
                            UserDetailViewState.Alert(msg = "${result.value.errorMsg}")
                        }
                    }
                }
            }
        }
    }
}

