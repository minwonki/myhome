package com.example.network.model

import com.google.gson.annotations.SerializedName

data class Card(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("img_url") val imgUrl: String,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: Int
)