package com.example.myhome.ui.screen.cardDetail

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
class CardDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo : MyHomeRepository
) : ViewModel() {

    var cardId : Int? = savedStateHandle.get("cardId")
        private set

    var state by mutableStateOf<CardDetailViewState>(CardDetailViewState.Empty)
        private set

    init {
        Timber.i("init : cardId = $cardId")
        cardId?.let {
            cardInfo(cardId = it)
        }
    }

    private fun cardInfo(cardId : Int) {
        viewModelScope.launch {
            repo.cardDetails(cardId = cardId).collect { result ->
                when (result) {
                    is ResultWrapper.GenericError -> {
                        state = CardDetailViewState.Alert(msg = "${result.message}")
                    }
                    ResultWrapper.Loading -> {
                        state = CardDetailViewState.Loading
                    }
                    is ResultWrapper.NetworkError -> {
                        state = CardDetailViewState.Alert(msg = "${result.message}")
                    }
                    is ResultWrapper.Success -> {
                        Timber.i("card:${result.value}")
                        state = if (result.value.Ok) {
                            CardDetailViewState.Success(
                                card = result.value.card,
                                user = result.value.user,
                                recommend = result.value.recommendCards
                            )
                        } else {
                            CardDetailViewState.Alert(msg = "${result.value.errorMsg}")
                        }
                    }
                }
            }
        }
    }

}
