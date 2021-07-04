package com.example.network.model

import com.google.gson.annotations.SerializedName

data class CardDetail(
    @SerializedName("ok") val Ok : Boolean,
    @SerializedName("error_msg") val errorMsg : String?,

    @SerializedName("card") val card : Card,
    @SerializedName("user") val user : User,
    @SerializedName("recommend_cards") val recommendCards : List<Card>
)