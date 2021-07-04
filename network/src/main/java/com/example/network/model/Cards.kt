package com.example.network.model

import com.google.gson.annotations.SerializedName

data class Cards(
    @SerializedName("ok") val Ok : Boolean,
    @SerializedName("error_msg") val errorMsg : String?,

    @SerializedName("cards") val cards : List<Card>
)