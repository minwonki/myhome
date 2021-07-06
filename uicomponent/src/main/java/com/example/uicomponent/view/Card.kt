package com.example.uicomponent.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.uicomponent.R
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.imageloading.ImageLoadState

@Composable
fun CardItem(
    cardImgUrl: String,
    cardOnClick: () -> Unit = {}
) {
    val painter = rememberGlidePainter(request = cardImgUrl)
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

@Composable
fun CardItemWithDesc(
    cardImgUrl : String,
    cardDescription : String,
    cardOnClick: () -> Unit = {}
) {
    val painter = rememberGlidePainter(request = cardImgUrl)
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clickable {
                    cardOnClick()
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
        Text(cardDescription)
    }
}
