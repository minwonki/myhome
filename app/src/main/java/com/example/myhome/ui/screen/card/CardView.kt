package com.example.myhome.ui.screen.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.myhome.R
import com.example.myhome.ui.graph.RootScreen
import com.example.network.model.Card
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.imageloading.ImageLoadState

@Composable
fun CardView(
    navController: NavController,
    viewModel: CardViewModel = hiltViewModel()
) {
    CardView(
        cards = viewModel.cards.collectAsLazyPagingItems(),
        cardOnClick = { cardId -> navController.navigate(route = RootScreen.CardDetail.createRoute(cardId = cardId)) }
    )
}

@Composable
internal fun CardView(
    cards: LazyPagingItems<Card>,
    cardOnClick: (Int) -> Unit = {}
) {
    Scaffold(
        topBar = {},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    items(cards) { card ->
                        card?.let {
                            CardItem(
                                card = card,
                                cardOnClick = cardOnClick
                            )
                        }
                    }

                    cards.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
                            }
                            loadState.append is LoadState.Loading -> {
                                item {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                            .wrapContentWidth(Alignment.CenterHorizontally)
                                    )
                                }
                            }
                            loadState.refresh is LoadState.Error -> {
                                val e = cards.loadState.refresh as LoadState.Error
                                item {
                                    ErrorItem(
                                        message = "${e.error.message}",
                                        modifier = Modifier.fillParentMaxSize(),
                                        onClickRetry = { refresh() }
                                    )
                                }
                            }
                            loadState.append is LoadState.Error -> {
                                val e = cards.loadState.append as LoadState.Error
                                item {
                                    ErrorItem(
                                        message = "${e.error.message}",
                                        onClickRetry = { retry() }
                                    )
                                }
                            }
                        }
                    }
                }

            }

        }
    )
}

@Composable
fun CardItem(
    card : Card,
    cardOnClick: (Int) -> Unit
) {
    val painter = rememberGlidePainter(request = card.imgUrl)
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clickable {
                    cardOnClick(card.id)
                },
            contentAlignment = Alignment.Center,
        ) {

            Image(
                painter = painter,
                contentDescription = stringResource(R.string.image_content_desc),
                contentScale = ContentScale.Crop
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
        Text(card.description)
    }
}

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            maxLines = 1,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.h6,
            color = Color.Red
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Try again")
        }
    }
}

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}