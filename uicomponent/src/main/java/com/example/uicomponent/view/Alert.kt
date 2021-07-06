package com.example.uicomponent.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyHomeAlert(
    title : String = "Notice",
    message : String = "Message",
    onDismiss : () -> Unit = {},
    confirm : () -> Unit = {}

) {
    AlertDialog(
        title = { Text(title) },
        text = { Text(message) },
        onDismissRequest = { onDismiss() },
        modifier = Modifier.padding(20.dp),
        confirmButton = {
            Button(
                onClick = { confirm() }
            ) {
                Text("OK")
            }
        }
    )
}