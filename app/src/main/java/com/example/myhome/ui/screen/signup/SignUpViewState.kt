package com.example.myhome.ui.screen.signup

sealed class SignUpViewState {
    object SignUpWaiting : SignUpViewState()
    data class SignUpSuccess(val msg: String) : SignUpViewState()
    data class Alert(val msg: String) : SignUpViewState()
}