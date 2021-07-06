package com.example.myhome.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myhome.ui.graph.RootScreen
import com.example.myhome.ui.screen.common.CardItems
import com.example.uicomponent.view.LoadingView
import com.example.uicomponent.view.MyHomeAlert
import com.example.uicomponent.view.UserItem

@Composable
fun HomeView(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier
) {
    HomeView(
        modifier = modifier,
        state = viewModel.state,
        showPopularCard = { cardId ->
            navController.navigate(route = RootScreen.CardDetail.createRoute(cardId = cardId))
        },
        showPopularUser = { userId ->
            navController.navigate(route = RootScreen.UserDetail.createRoute(userId = userId))
        }
    )
}

@Composable
internal fun HomeView(
    state: HomeViewState,
    showPopularCard: (Int) -> Unit = {},
    showPopularUser: (Int) -> Unit = {},
    closeAlert: () -> Unit = {},
    modifier: Modifier
) {
    Scaffold(
        topBar = {},
        content = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                when (state) {
                    is HomeViewState.Alert -> {
                        MyHomeAlert(
                            message = state.msg,
                            onDismiss = { closeAlert() },
                            confirm = { closeAlert() }
                        )
                    }
                    HomeViewState.Empty -> {
                    }
                    HomeViewState.Loading -> {
                        LoadingView(modifier = Modifier.fillMaxSize())
                    }
                    is HomeViewState.Success -> {
                        CardItems(
                            title = "인기 카드를 클릭해 보세요.",
                            cards = state.cards,
                            showOtherCard = { cardId ->
                                showPopularCard(cardId)
                            }
                        )

                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .padding(horizontal = 4.dp)
                        )

                        Text("인기 유저 클릭해 보세요.")
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(state.users) { user ->
                                UserItem(
                                    userNickName = user.nickName,
                                    userIntroduction = user.introduction,
                                    userOnClick = { showPopularUser(user.id) }
                                )
                            }
                        }
                    }
                }

            }
        }
    )
}





