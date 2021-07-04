package com.example.myhome.ui.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.myhome.ui.screen.signin.SignInView
import com.example.myhome.ui.screen.signup.SignUpView

sealed class LoginScreen(val route: String) {
    object SignIn: LoginScreen("login/sign_in")
    object SignUp: LoginScreen("login/sign_up")
}

fun NavGraphBuilder.addLoginGraph(
    navController: NavHostController
) {
    composable(LoginScreen.SignIn.route) {
        SignInView(
            navController = navController,
        )
    }

    composable(LoginScreen.SignUp.route) {
        SignUpView(
            navController = navController
        )
    }

}