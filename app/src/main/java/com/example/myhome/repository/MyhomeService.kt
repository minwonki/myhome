package com.example.myhome.repository

import com.example.network.model.*
import retrofit2.http.*

interface MyHomeService {
    @FormUrlEncoded
    @POST("users/sign_in")
    suspend fun signIn(@FieldMap params: HashMap<String, Any>): Auth

    @FormUrlEncoded
    @POST("users")
    suspend fun signUp(@FieldMap params: HashMap<String, Any>): Auth

    @GET("home")
    suspend fun home() : Home

    @GET("cards")
    suspend fun cards(@Query("page") page : Int) : Cards

    @GET("cards/{cardId}")
    suspend fun cardDetail(@Path("cardId") cardId: Int): CardDetail

    @GET("users/{userId}")
    suspend fun userDetail(@Path("userId") userId: Int): UserDetail
}