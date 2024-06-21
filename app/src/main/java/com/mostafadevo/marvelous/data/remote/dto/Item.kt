package com.mostafadevo.marvelous.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("name")
    val name: String,
    @SerializedName("resourceURI")
    val resourceURI: String
)