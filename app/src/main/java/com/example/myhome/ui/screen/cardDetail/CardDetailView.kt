package com.example.myhome.ui.screen.cardDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myhome.MainMenuTabs
import com.example.myhome.ui.graph.RootScreen
import com.example.myhome.ui.screen.common.CardItems
import com.example.myhome.ui.screen.userDetail.MyHomeTopBar
import com.example.uicomponent.view.CardItem
import com.example.uicomponent.view.LoadingView
import com.example.uicomponent.view.MyHomeAlert


@Composable
fun CardDetailView(
    navController: NavController,
    viewModel: CardDetailViewModel = hiltViewModel()
) {
    var backStack = navController.previousBackStackEntry?.destination?.route ?: MainMenuTabs.CARD.route
    val routes = remember { MainMenuTabs.values().map { it.route } }

    if (!routes.contains(backStack)) {
        backStack = MainMenuTabs.CARD.route
    }

    CardDetailView(
        state = viewModel.state,
        back = {
            navController.navigate(route = backStack) {
                popUpTo(backStack) { inclusive = true }
            }
        },
        otherCard = { cardId ->
            navController.navigate(route = RootScreen.CardDetail.createRoute(cardId = cardId))
        }
    )
}

@Composable
fun CardDetailView(
    state: CardDetailViewState,
    back: () -> Unit = {},
    otherCard: (Int) -> Unit = {}
) {
    Scaffold(
        topBar = { MyHomeTopBar(title = "Card Info", leftAction = { back() }) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            when (state) {
                is CardDetailViewState.Alert -> {
                    MyHomeAlert(
                        message = state.msg,
                        onDismiss = { back() },
                        confirm = { back() }
                    )
                }
                CardDetailViewState.Empty -> {
                }
                CardDetailViewState.Loading -> {
                    LoadingView(modifier = Modifier.fillMaxSize())
                }
                is CardDetailViewState.Success -> {
                    CardItem(
                        cardImgUrl = state.card.imgUrl,
                        cardOnClick = { }
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

