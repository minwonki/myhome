package com.example.myhome.ui.screen.signup

import com.example.myhome.CoroutinesTestRule
import com.example.myhome.repository.FakeMyHomeRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SignUpViewModelTest {
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun testSignIn() {
        val viewModel = SignUpViewModel(
            repo = FakeMyHomeRepositoryImpl()
        )
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.signUp("nickName",introduction = "intro", password = "pass")
            println("result = ${viewModel.state}")

            Assert.assertEquals(
                "duplicate",
                (viewModel.state as SignUpViewState.Alert).msg
            )
        }
    }

    @Test
    fun testCloseAlert() {
        val viewModel = SignUpViewModel(
            repo = FakeMyHomeRepositoryImpl()
        )

        viewModel.closeAlertDialog()

        Assert.assertEquals(
            SignUpViewState.SignUpWaiting,
            viewModel.state
        )
    }
}