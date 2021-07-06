package com.example.uicomponent.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UserItem(
    userNickName: String,
    userIntroduction: String,
    userOnClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(50.dp)
            .border(1.dp, Color.Gray)
            .clickable { userOnClick() },
        contentAlignment = Alignment.Center,
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text("Nick: $userNickName")
            Text("Intro: $userIntroduction")
        }
    }
}
