package com.mostafadevo.marvelous.data.remote

import com.mostafadevo.marvelous.data.remote.dto.MarvelResponse
import retrofit2.Response
import retrofit2.http.GET


interface MarvelApi {
    @GET("/v1/public/characters")
    suspend fun getCharacters(): Response<MarvelResponse>
}