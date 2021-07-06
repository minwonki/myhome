package com.example.myhome.ui.screen.signin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myhome.ui.graph.LoginScreen
import com.example.myhome.ui.graph.RootScreen
import com.example.uicomponent.view.MyHomeAlert

@Composable
fun SignInView(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    SignInView(
        state = viewModel.state,
        signInAction = { nickName, passWord ->
            viewModel.signIn(nickName = nickName, password = passWord)
        },
        signUpAction = { navController.navigate(LoginScreen.SignUp.route) },
        closeAlert = { viewModel.closeAlertDialog() },
        moveMain = {
            navController.navigate(RootScreen.Main.route) {
                launchSingleTop = true
                popUpTo(RootScreen.SignIn.route) {
                    inclusive = true
                }
            }
        }
    )
}

@Composable
internal fun SignInView(
    state: SignInViewState,
    signInAction : (String, String) -> Unit = { _: String, _: String -> },
    signUpAction : () -> Unit = {},
    closeAlert : () -> Unit = {},
    moveMain : () -> Unit = {}
) {
    when(state) {
        is SignInViewState.Alert -> {
            MyHomeAlert(
                message = state.msg,
                onDismiss = { closeAlert() },
                confirm = { closeAlert() }
            )
        }
        SignInViewState.SignedIn -> { moveMain() }
        SignInViewState.SignedOut -> {}
    }

    Scaffold (
        topBar = {},
        content = {
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
            ) {
                val nickName = remember { mutableStateOf("") }
                val passWord = remember { mutableStateOf("")}

                fun isSubmit(): Boolean {
                    return nickName.value.isNotEmpty() && passWord.value.isNotEmpty()
                }

                OutlinedTextField(
                    value = nickName.value,
                    onValueChange = { value ->
                        nickName.value = value
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("NickName") } ,
                    placeholder = { Text(text = "NickName")}
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = passWord.value,
                    onValueChange = { value ->
                        passWord.value = value
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") } ,
                    placeholder = { Text(text = "Password")},
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { signInAction(nickName.value, passWord.value) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isSubmit()
                ) {
                    Text("SignIn")
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { signUpAction() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("SignUp")
                }
            }

        }
    )

}

