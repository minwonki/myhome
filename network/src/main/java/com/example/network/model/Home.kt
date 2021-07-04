package com.example.network.model

import com.google.gson.annotations.SerializedName

data class Home(
    @SerializedName("ok") val Ok : Boolean,
    @SerializedName("error_msg") val errorMsg : String?,

    @SerializedName("popular_cards") val popularCards : List<Card>,
    @SerializedName("popular_users") val popularUsers : List<User>,
)