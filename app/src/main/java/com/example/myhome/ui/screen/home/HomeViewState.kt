package com.example.myhome.ui.screen.home

import com.example.network.model.Card
import com.example.network.model.User

sealed class HomeViewState {
    object Empty : HomeViewState()
    object Loading : HomeViewState()
    data class Success(val cards : List<Card>, val users: List<User>) : HomeViewState()
    data class Alert(val msg: String) : HomeViewState()
}