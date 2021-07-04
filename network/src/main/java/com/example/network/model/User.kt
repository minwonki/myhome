package com.example.network.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("nickname") val nickName : String,
    @SerializedName("introduction") val introduction : String,
    @SerializedName("id") val id : Int
)
