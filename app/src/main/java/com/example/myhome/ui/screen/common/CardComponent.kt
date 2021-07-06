package com.example.myhome.ui.screen.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.network.model.Card
import com.example.uicomponent.view.CardItem

@Composable
fun CardItems(
    title: String,
    cards: List<Card>,
    showOtherCard: (Int) -> Unit = {}
) {
    Text(title)
    Spacer(modifier = Modifier.height(4.dp))
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(cards) { card ->
            CardItem(
                cardImgUrl = card.imgUrl,
                cardOnClick = { showOtherCard(card.id) }
            )
        }
    }
}