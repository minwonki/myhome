package com.example.myhome.ui.screen.userDetail

import com.example.network.model.User

sealed class UserDetailViewState {
    object Empty : UserDetailViewState()
    object Loading : UserDetailViewState()
    data class Success(val user: User) : UserDetailViewState()
}

data class AppState(
    val viewState : UserDetailViewState = UserDetailViewState.Empty,
    val alert : Pair<Boolean, String> = Pair(first = false, second = "message")
)