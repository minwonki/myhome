package com.example.myhome.repository

import androidx.paging.PagingData
import com.example.network.helper.ResultWrapper
import com.example.network.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class FakeMyHomeRepositoryImpl: MyHomeRepository {
    override suspend fun signIn(params: HashMap<String, Any>): Flow<ResultWrapper<Auth>> {
        return flow {
            emit(ResultWrapper.Success(Auth(Ok = true, errorMsg = null, userId = 123)))
        }.onStart { ResultWrapper.Loading }
    }

    override suspend fun signUp(params: HashMap<String, Any>): Flow<ResultWrapper<Auth>> {
        return flow {
            emit(ResultWrapper.Success(Auth(Ok = true, errorMsg = null, userId = 123)))
        }.onStart { ResultWrapper.Loading }
    }

    override suspend fun homes(): Flow<ResultWrapper<Home>> {
        return flow {
            emit(
                ResultWrapper.Success(
                    Home(
                        Ok = true, errorMsg = null,
                        popularCards = listOf(
                            Card(
                                userId = 1,
                                imgUrl = "",
                                description = "desc",
                                id = 0
                            )
                        ),
                        popularUsers = listOf(User(nickName = "mwk", introduction = "mwk", id = 0))
                    )
                )
            )
        }.onStart { ResultWrapper.Loading }
    }

    override suspend fun cards(): Flow<PagingData<Card>> {
        return flow {
            emit(
                PagingData.from(
                    data = listOf(Card(userId = 1, imgUrl = "", description = "desc", id = 0))
                )
            )
        }
    }

    override suspend fun cardDetails(cardId: Int): Flow<ResultWrapper<CardDetail>> {
        return flow {
            emit(
                ResultWrapper.Success(
                    CardDetail(
                        Ok = true, errorMsg = null,
                        card = Card(userId = 1, imgUrl = "", description = "desc", id = 0),
                        user = User(nickName = "mwk", introduction = "mwk", id = 0),
                        recommendCards = listOf(
                            Card(
                                userId = 1,
                                imgUrl = "",
                                description = "desc",
                                id = 0
                            )
                        )
                    )
                )
            )
        }.onStart { ResultWrapper.Loading }
    }

    override suspend fun userDetails(userId: Int): Flow<ResultWrapper<UserDetail>> {
        return flow {
            emit(
                ResultWrapper.Success(
                    UserDetail(
                        Ok = true, errorMsg = null,
                        user = User(nickName = "mwk", introduction = "mwk", id = 0)
                    )
                )
            )
        }
    }

}