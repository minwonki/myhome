package com.example.myhome.ui.screen.signup

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

@Composable
fun SignUpView(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    SignUpView(
        state = viewModel.state,
        signUpAction = { nickName, introduction, passWord ->
            viewModel.signUp(
                nickName = nickName, introduction = introduction, password = passWord
            )
        },
        closeAlert = { viewModel.closeAlertDialog() },
        moveSignIn = {
            navController.navigate(RootScreen.SignIn.route) {
                launchSingleTop = true
                popUpTo(LoginScreen.SignUp.route) {
                    inclusive = true
                }
            }
        },
    )
}

@Composable
internal fun SignUpView(
    state: SignUpViewState,
    signUpAction: (String, String, String) -> Unit =  { _: String, _: String, _: String -> },
    closeAlert : () -> Unit = {},
    moveSignIn : () -> Unit = {}
) {
    when(state) {
        is SignUpViewState.Alert -> {
            AlertDialog(
                title = { Text(text = "Alert")},
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
        is SignUpViewState.SignUpSuccess -> {
            AlertDialog(
                title = { Text(text = "Alert")},
                text = { Text(text = state.msg) },
                onDismissRequest = { closeAlert() },
                modifier = Modifier.padding(20.dp),
                confirmButton = {
                    Button(
                        onClick = { moveSignIn() }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
        SignUpViewState.SignUpWaiting -> {}
    }

    Scaffold (
        topBar = {},
        content = {
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
            ) {
                val nickName = remember { mutableStateOf("") }
                val introduction = remember { mutableStateOf("") }
                val passWord = remember { mutableStateOf("") }

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
                    value = introduction.value,
                    onValueChange = { value ->
                        introduction.value = value
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Introduction") } ,
                    placeholder = { Text(text = "Introduction")}
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
                    onClick = {
                        signUpAction(nickName.value, introduction.value, passWord.value)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isSubmit()
                ) {
                    Text("SignUp")
                }

            }

        }
    )
}