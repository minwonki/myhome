package com.example.myhome.ui.screen.signin

import com.example.myhome.CoroutinesTestRule
import com.example.myhome.repository.FakeMyHomeRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SignInViewModelTest {
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun testSignIn() {
        val viewModel = SignInViewModel(
            repo = FakeMyHomeRepositoryImpl()
        )
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.signIn(nickName = "nick",password = "pass")
            println("result = ${viewModel.state}")

            Assert.assertEquals(
                SignInViewState.SignedIn,
                viewModel.state
            )
        }
    }

    @Test
    fun testCloseAlert() {
        val viewModel = SignInViewModel(
            repo = FakeMyHomeRepositoryImpl()
        )

        viewModel.closeAlertDialog()

        Assert.assertEquals(
            SignInViewState.SignedOut,
            viewModel.state
        )
    }

}