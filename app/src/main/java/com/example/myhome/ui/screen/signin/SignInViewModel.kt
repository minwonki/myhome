package com.example.myhome.ui.screen.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.repository.MyHomeRepository
import com.example.network.helper.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo : MyHomeRepository
): ViewModel() {

    var state by mutableStateOf<SignInViewState>(SignInViewState.SignedOut)
        private set

    init {
        Timber.i("init")
    }

    fun closeAlertDialog() {
        state = SignInViewState.SignedOut
    }

    fun signIn(nickName: String, password: String) {
        viewModelScope.launch {
            val params = HashMap<String, Any>()
            params["nickname"] = nickName
            params["pwd"] = password

            repo.signIn(params = params).collect { result ->
                when (result) {
                    is ResultWrapper.GenericError -> {
                        state = SignInViewState.Alert(msg = "${result.message}")
                    }
                    is ResultWrapper.NetworkError -> {
                        state = SignInViewState.Alert(msg = "${result.message}")
                    }
                    is ResultWrapper.Success -> {
                        state = if (result.value.Ok) {
                            SignInViewState.SignedIn
                        } else {
                            SignInViewState.Alert(msg = "${result.value.errorMsg}")
                        }
                    }
                    else -> {}
                }
            }
        }

    }
}

