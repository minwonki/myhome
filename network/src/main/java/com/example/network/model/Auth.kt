package com.example.network.model

import com.google.gson.annotations.SerializedName

data class Auth (
    @SerializedName("ok") val Ok : Boolean,
    @SerializedName("error_msg") val errorMsg : String?,

    @SerializedName("user_id") val userId : Int
)