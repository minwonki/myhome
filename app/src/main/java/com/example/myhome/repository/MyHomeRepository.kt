package com.example.myhome.repository

import com.example.network.helper.ResultWrapper
import com.example.network.model.*
import kotlinx.coroutines.flow.Flow

interface MyHomeRepository {
    // from network
    suspend fun signIn(params: HashMap<String, Any>) : Flow<ResultWrapper<Auth>>
    suspend fun signUp(params: HashMap<String, Any>) : Flow<ResultWrapper<Auth>>

    suspend fun homes() : Flow<ResultWrapper<Home>>
    suspend fun cards() : Flow<ResultWrapper<Cards>>

    suspend fun cardDetails(cardId: Int) : Flow<ResultWrapper<CardDetail>>
    suspend fun userDetails(userId: Int) : Flow<ResultWrapper<UserDetail>>
}

