package com.example.myhome.ui.screen.signin

sealed class SignInViewState {
    object SignedIn : SignInViewState()
    object SignedOut : SignInViewState()
    data class Alert(val msg: String) : SignInViewState()
}