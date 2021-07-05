package com.example.myhome.ui.screen.cardDetail

import com.example.network.model.Card
import com.example.network.model.User

sealed class CardDetailViewState {
    object Empty : CardDetailViewState()
    object Loading : CardDetailViewState()
    data class Success(val card: Card, val user: User, val recommend: List<Card>) : CardDetailViewState()
    data class Alert(val msg: String) : CardDetailViewState()
}