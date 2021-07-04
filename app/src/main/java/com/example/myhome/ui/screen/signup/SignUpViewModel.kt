package com.example.myhome.ui.screen.signup

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
class SignUpViewModel @Inject constructor(
    private val repo : MyHomeRepository
): ViewModel() {

    var state by mutableStateOf<SignUpViewState>(SignUpViewState.SignUpWaiting)
        private set

    init {
        Timber.i("SignUpViewModel")
    }

    fun closeAlertDialog() {
        state = SignUpViewState.SignUpWaiting
    }

    fun signUp(nickName: String, introduction: String, password: String) {
        viewModelScope.launch {
            val params = HashMap<String, Any>()
            params["nickname"] = nickName
            params["introduction"] = introduction
            params["pwd"] = password

            repo.signUp(params = params).collect { result ->
                when (result) {
                    is ResultWrapper.GenericError -> {
                        state = SignUpViewState.Alert(msg = "${result.message}")
                    }
                    is ResultWrapper.NetworkError -> {
                        state = SignUpViewState.Alert(msg = "${result.message}")
                    }
                    is ResultWrapper.Success -> {
                        state = if (result.value.Ok) {
                            SignUpViewState.SignUpSuccess(msg = "${nickName}으로 회원가입 성공 했습니다.\nSignIn 페이지로 돌아가 로그인 해주세요.")
                        } else {
                            SignUpViewState.Alert(msg = "${result.value.errorMsg}")
                        }
                    }
                    else -> {}
                }
            }
        }

    }
}

