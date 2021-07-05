package com.example.myhome.ui.screen.card

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.myhome.repository.MyHomeRepository
import com.example.network.helper.ResultWrapper
import com.example.network.model.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repo: MyHomeRepository
) : ViewModel() {

    var state by mutableStateOf<CardViewState>(CardViewState.Empty)
        private set

    lateinit var cards: Flow<PagingData<Card>>

    init {
        Timber.i("init")
        getCards()
    }

    fun getCards() {
        viewModelScope.launch {
            cards = repo.cards()
        }
    }

//    fun getCards() {
//        viewModelScope.launch {
//            repo.cards().collect { result ->
//                when (result) {
//                    is ResultWrapper.GenericError -> {
//                        state = CardViewState.Alert("${result.message}")
//                    }
//                    ResultWrapper.Loading -> { state = CardViewState.Loading }
//                    is ResultWrapper.NetworkError -> {
//                        state = CardViewState.Alert("${result.message}")
//                    }
//                    is ResultWrapper.Success -> {
//                        state = if (result.value.Ok) {
//                            CardViewState.Success(cards = result.value.cards)
//                        } else {
//                            CardViewState.Alert(msg = "${result.value.errorMsg}")
//                        }
//                    }
//                }
//            }
//        }
//    }
}

sealed class CardViewState {
    object Empty : CardViewState()
    object Loading : CardViewState()
    data class Success(val cards : List<Card>) : CardViewState()
    data class Alert(val msg: String) : CardViewState()
}