package com.example.myhome.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.network.model.Card

class CardPagingSource (
    private val service : MyHomeService
) : PagingSource<Int, Card>() {
    override fun getRefreshKey(state: PagingState<Int, Card>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Card> {
        return try {
            val nextPage = params.key ?: 1
            val card = service.cards(page = nextPage)

            val data = card.cards
            LoadResult.Page(
                data = data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (data.isEmpty()) null else nextPage + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}