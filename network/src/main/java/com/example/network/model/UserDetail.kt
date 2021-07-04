package com.example.network.model

import com.google.gson.annotations.SerializedName

data class UserDetail(
    @SerializedName("ok") val Ok : Boolean,
    @SerializedName("error_msg") val errorMsg : String?,

    @SerializedName("user") val user : User
)