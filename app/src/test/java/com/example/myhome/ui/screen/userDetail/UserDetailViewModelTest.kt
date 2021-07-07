package com.example.myhome.ui.screen.userDetail

import androidx.lifecycle.SavedStateHandle
import com.example.myhome.CoroutinesTestRule
import com.example.myhome.repository.FakeMyHomeRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserDetailViewModelTest {
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun testCloseAlert() {
        val viewModel = UserDetailViewModel(
            savedStateHandle = SavedStateHandle(),
            repo = FakeMyHomeRepositoryImpl()
        )

        viewModel.closeAlert()
        println("result = ${viewModel.state.value.alert}")

        Assert.assertEquals(false, viewModel.state.value.alert.first)
    }

    @Test
    fun testUserInfo() {
        val viewModel = UserDetailViewModel(
            savedStateHandle = SavedStateHandle(),
            repo = FakeMyHomeRepositoryImpl()
        )

        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.userInfo(1)
            println("result = ${viewModel.state.value}")

            Assert.assertEquals(
                0,
                (viewModel.state.value.viewState as UserDetailViewState.Success).user.id
            )
        }
    }
}