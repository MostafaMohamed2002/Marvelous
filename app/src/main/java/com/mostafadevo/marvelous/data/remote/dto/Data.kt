package com.mostafadevo.marvelous.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("count")
    val count: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val charactersDTOS: List<CharacterDTO>,
    @SerializedName("total")
    val total: Int
)