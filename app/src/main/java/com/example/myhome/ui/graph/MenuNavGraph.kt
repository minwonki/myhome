package com.example.myhome.ui.graph

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.myhome.ui.screen.card.CardView
import com.example.myhome.ui.screen.home.HomeView

sealed class MainScreen(val route: String) {
    object MainHome : MainScreen("main/home")
    object MainCard : MainScreen("main/card")
}

fun NavGraphBuilder.addMenuNavGraph (
    navController: NavHostController,
    modifier: Modifier
) {
    composable(MainScreen.MainHome.route) {
        HomeView(modifier = modifier, navController = navController)
    }

    composable(MainScreen.MainCard.route) {
        CardView(modifier = modifier, navController = navController)
    }

}

