package com.example.myhome.ui.screen.userDetail

import com.example.network.model.User

sealed class UserDetailViewState {
    object Empty : UserDetailViewState()
    object Loading : UserDetailViewState()
    data class Success(val user: User) : UserDetailViewState()
    data class Alert(val msg: String) : UserDetailViewState()
}