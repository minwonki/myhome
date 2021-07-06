package com.example.myhome.ui.screen.cardDetail

import androidx.lifecycle.SavedStateHandle
import com.example.myhome.CoroutinesTestRule
import com.example.myhome.repository.FakeMyHomeRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CardDetailViewModelTest {
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun testCardInfo() {
        val viewModel = CardDetailViewModel(
            savedStateHandle = SavedStateHandle(),
            repo = FakeMyHomeRepositoryImpl()
        )

        coroutinesTestRule.testDispatcher.runBlockingTest {
            viewModel.cardInfo(1)
            println("result = ${viewModel.state}")

            Assert.assertEquals(
                0,
                (viewModel.state as CardDetailViewState.Success).user.id
            )
        }
    }
}