package com.example.myhome.ui.screen.card

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.myhome.ui.graph.RootScreen
import com.example.network.model.Card
import com.example.uicomponent.view.CardItemWithDesc
import com.example.uicomponent.view.ErrorItem
import com.example.uicomponent.view.LoadingView

@Composable
fun CardView(
    navController: NavController,
    viewModel: CardViewModel = hiltViewModel(),
    modifier: Modifier
) {
    CardView(
        modifier = modifier,
        cards = viewModel.cards.collectAsLazyPagingItems(),
        cardOnClick = { cardId -> navController.navigate(route = RootScreen.CardDetail.createRoute(cardId = cardId)) }
    )
}

@Composable
internal fun CardView(
    cards: LazyPagingItems<Card>,
    cardOnClick: (Int) -> Unit = {},
    modifier: Modifier
) {
    Scaffold(
        topBar = {},
        content = {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    items(cards) { card ->
                        card?.let {
                            CardItemWithDesc(
                                cardImgUrl = card.imgUrl,
                                cardDescription = card.description,
                                cardOnClick = { cardOnClick(card.id) }
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




