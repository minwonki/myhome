package com.example.myhome.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myhome.R
import com.example.network.model.Card
import com.example.network.model.User
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.imageloading.ImageLoadState
import timber.log.Timber

@Composable
fun HomeView(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeView(
        state = viewModel.state,
        showPopularCard = { id -> Timber.i("CardId:$id") },
        showPopularUser = { id -> Timber.i("UserId:$id") }
    )
}

@Composable
internal fun HomeView(
    state: HomeViewState,
    showPopularCard: (Int) -> Unit = {},
    showPopularUser: (Int) -> Unit = {},
    closeAlert: () -> Unit = {}
) {
    Scaffold(
        topBar = {},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                when (state) {
                    is HomeViewState.Alert -> {
                        AlertDialog(
                            title = { Text(text = "Alert") },
                            text = { Text(text = state.msg) },
                            onDismissRequest = { closeAlert() },
                            modifier = Modifier.padding(20.dp),
                            confirmButton = {
                                Button(
                                    onClick = { closeAlert() }
                                ) {
                                    Text("OK")
                                }
                            }
                        )
                    }
                    HomeViewState.Empty -> { }
                    HomeViewState.Loading -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is HomeViewState.Success -> {
                        Text("인기 카드를 클릭해 보세요.")
                        Spacer(modifier = Modifier.height(4.dp))
                        LazyRow(modifier = Modifier.fillMaxWidth()) {
                            items(state.cards) { card ->
                                CardItem(
                                    card = card,
                                    cardOnClick = { showPopularCard(card.id) }
                                )
                            }
                        }
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
                                    user = user,
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

@Composable
fun UserItem(
    user: User,
    userOnClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(50.dp)
            .border(1.dp, androidx.compose.ui.graphics.Color.Gray)
            .clickable { userOnClick() },
        contentAlignment = Alignment.Center,
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text("Nick: ${user.nickName}")
            Text(user.introduction)
        }
    }
}


@Composable
fun CardItem(
    card: Card,
    cardOnClick: () -> Unit = {}
) {
    val painter = rememberGlidePainter(request = card.imgUrl)
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(90.dp)
            .clickable {
                cardOnClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.image_content_desc),
        )

        when (painter.loadState) {
            is ImageLoadState.Loading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            is ImageLoadState.Error -> {
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = stringResource(R.string.image_content_desc)
                )
            }
            else -> {
            }
        }
    }
}

