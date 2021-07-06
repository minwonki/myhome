package com.example.myhome.ui.screen.card

import com.example.myhome.CoroutinesTestRule
import com.example.myhome.repository.FakeMyHomeRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CardViewModelTest {
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @InternalCoroutinesApi
    @Test
    fun testGetCards() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val viewModel = CardViewModel(
                repo = FakeMyHomeRepositoryImpl()
            )

            viewModel.getCards()
            // Paging3 테스트 코드 만들기가 수월하지 않음
            // PagingData 에서는 Data 접근할수 없음.
            // PagingSource 를 테스트 가능하게 만들어야 할것 같음.
            // https://developer.android.com/topic/libraries/architecture/paging/test 참고
            viewModel.cards.collect {
                println("collect = $it")
            }

        }
    }
}