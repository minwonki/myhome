package com.example.myhome.repository

import com.example.network.helper.ResultWrapper
import com.example.network.helper.safeApiCallFlow
import com.example.network.model.*
import kotlinx.coroutines.flow.Flow

class MyHomeRepositoryImpl(val service: MyHomeService) : MyHomeRepository {
    override suspend fun signIn(params: HashMap<String, Any>): Flow<ResultWrapper<Auth>> {
        return safeApiCallFlow { service.signIn(params = params) }
    }

    override suspend fun signUp(params: HashMap<String, Any>): Flow<ResultWrapper<Auth>> {
        return safeApiCallFlow { service.signUp(params = params) }
    }

    override suspend fun homes(): Flow<ResultWrapper<Home>> {
        return safeApiCallFlow { service.home() }
    }

    override suspend fun cards(): Flow<ResultWrapper<Cards>> {
        return safeApiCallFlow { service.cards() }
    }

    override suspend fun cardDetails(cardId: Int): Flow<ResultWrapper<CardDetail>> {
        return safeApiCallFlow { service.cardDetail(cardId = cardId) }
    }

    override suspend fun userDetails(userId: Int): Flow<ResultWrapper<UserDetail>> {
        return safeApiCallFlow { service.userDetail(userId = userId) }
    }

}