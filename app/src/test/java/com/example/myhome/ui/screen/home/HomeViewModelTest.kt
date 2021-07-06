package com.example.myhome.ui.screen.home

import com.example.myhome.CoroutinesTestRule
import com.example.myhome.repository.FakeMyHomeRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun testGetHome() {
        val viewModel = HomeViewModel(
            repo = FakeMyHomeRepositoryImpl()
        )
        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.getHome()
            println("result = ${viewModel.state}")

            Assert.assertEquals(
                0,
                (viewModel.state as HomeViewState.Success).cards[0].id
            )
        }
    }

}