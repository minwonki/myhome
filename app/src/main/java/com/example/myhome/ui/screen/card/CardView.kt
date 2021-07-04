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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myhome.R
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.imageloading.ImageLoadState
import timber.log.Timber

@Composable
fun CardView(
    navController: NavController,
    viewModel: CardViewModel = hiltViewModel()
) {
    CardView(
        state = viewModel.state,
        cardOnClick = { id -> Timber.i("CardId:$id") },
        closeAlert = { viewModel.getCards() }
    )
}

@Composable
internal fun CardView(
    state: CardViewState,
    cardOnClick: (Int) -> Unit = {},
    closeAlert: () -> Unit = {}
) {
    Scaffold(
        topBar = {},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                when (state) {
                    is CardViewState.Alert -> {
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
                    CardViewState.Empty -> {
                    }
                    CardViewState.Loading -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is CardViewState.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                        ) {
                            items(state.cards) { card ->
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
//                                                Image(
//                                                    painter = painterResource(id = R.drawable.placeholder),
//                                                    contentDescription = stringResource(R.string.image_content_desc),
//                                                    contentScale = ContentScale.FillWidth
//                                                )
                                            }
                                            else -> {
                                            }
                                        }
                                    }
                                    Text(card.description)
                                }

                            }
                        }
                    }
                }
            }

        }
    )
}
