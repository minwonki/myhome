package com.example.myhome.ui.screen.cardDetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myhome.ui.graph.RootScreen
import com.example.myhome.ui.screen.card.CardItem
import com.example.myhome.ui.screen.home.CardItems
import com.example.myhome.ui.screen.userDetail.MyHomeTopBar
import timber.log.Timber

@Composable
fun CardDetailView(
    navController: NavController,
    viewModel: CardDetailViewModel = hiltViewModel()
) {
    CardDetailView(
        state = viewModel.state,
        back = { navController.popBackStack() },
        otherCard = { cardId ->
            navController.navigate(route = RootScreen.CardDetail.createRoute(cardId = cardId))
        }
    )
}

@Composable
fun CardDetailView(
    state: CardDetailViewState,
    back: () -> Unit = {},
    otherCard : (Int) -> Unit = {}
) {
    Scaffold(
        topBar = { MyHomeTopBar(title = "Card Info", leftAction = { back() }) }
    ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)) {
                when (state) {
                    is CardDetailViewState.Alert -> {
                        AlertDialog(
                            title = { Text(text = "Alert")},
                            text = { Text(text = state.msg) },
                            onDismissRequest = {  },
                            modifier = Modifier.padding(20.dp),
                            confirmButton = {
                                Button(
                                    onClick = {  }
                                ) {
                                    Text("OK")
                                }
                            }
                        )
                    }
                    CardDetailViewState.Empty -> { }
                    CardDetailViewState.Loading -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is CardDetailViewState.Success -> {
                        CardItem(
                            card = state.card,
                            cardOnClick = { cardId -> Timber.i("Click Card:$cardId") }
                        )
                        Divider()
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text("작성자 : ${state.user.nickName}")
                            Text("소개 : ${state.user.introduction}")
                        }
                        Divider()
                        CardItems(
                            title = "추천 카드를 클릭해 보세요.",
                            cards = state.recommend,
                            showOtherCard = { cardId ->
                                otherCard(cardId)
                            }
                        )
                    }
                }
            }
    }
}