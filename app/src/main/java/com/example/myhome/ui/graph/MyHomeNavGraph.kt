package com.example.myhome.ui.graph

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.myhome.MainMenuTabs

sealed class RootScreen(val route: String) {
    object SignIn: RootScreen("login")
    object Main: RootScreen("main")
    object CardDetail: RootScreen("card_detail")
}

@Composable
fun MyHomeNavGraph(
    modifier: Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = RootScreen.SignIn.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        // login group
        navigation(
            route = RootScreen.SignIn.route,
            startDestination = LoginScreen.SignIn.route
        ) {
            addLoginGraph(navController = navController)
        }

        // main tab
        navigation(
            route = RootScreen.Main.route,
            startDestination = MainMenuTabs.HOME.route
        ) {
            addMenuNavGraph(navController = navController)
        }

       composable(
           route = RootScreen.CardDetail.route
       ) {
           Text("CardDetail")
       }

    }
}