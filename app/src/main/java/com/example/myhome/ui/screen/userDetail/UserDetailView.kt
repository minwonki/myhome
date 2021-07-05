package com.example.myhome.ui.screen.userDetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun UserDetailView(
    navController: NavController,
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    UserDetailView(
        state = viewModel.state,
        back = { navController.popBackStack() }
    )
}

@Composable
internal fun UserDetailView(
    state : UserDetailViewState,
    back : () -> Unit = {}
) {
    Scaffold(
        topBar = { 
                MyHomeTopBar(
                    title = "User Info",
                    leftAction = { back() }
                )
        },
        content = {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)) {
                when (state) {
                    is UserDetailViewState.Alert -> {
                        AlertDialog(
                            title = { Text(text = "Alert")},
                            text = { Text(text = state.msg) },
                            onDismissRequest = {  },
                            modifier = Modifier.padding(20.dp),
                            confirmButton = {
                                Button(
                                    onClick = {  }
                                ) {
                                    Text("OK")
                                }
                            }
                        )
                    }
                    UserDetailViewState.Empty -> { }
                    UserDetailViewState.Loading -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is UserDetailViewState.Success -> {
                        Text("User Nickname : ${state.user.nickName}")
                        Text("User Introduction : ${state.user.introduction}")
                    }
                }
            }
        }
    )

}

@Composable
fun MyHomeTopBar(
    title: String = "Title",
    leftAction: () -> Unit = {},
    leftImageVector: ImageVector = Icons.Rounded.ArrowBack,
    rightAction: () -> Unit = {},
    rightImageVector: ImageVector = Icons.Rounded.ArrowBack
) {
    TopAppBar(
        backgroundColor = Color.White,
        elevation = 2.dp,
        contentPadding = PaddingValues(horizontal = 0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { leftAction() }) {
                Icon(leftImageVector, contentDescription = "Localized description")
            }
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = title, style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.weight(1.0f))

            IconButton(onClick = { rightAction() }) {
                Icon(
                    rightImageVector,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        }

    }
}