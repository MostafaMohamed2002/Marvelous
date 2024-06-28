package com.mostafadevo.marvelous.domain

import android.util.Log
import com.mostafadevo.marvelous.Utils.toEntity
import com.mostafadevo.marvelous.data.local.CharacterEntity
import com.mostafadevo.marvelous.data.local.MarvelDao
import com.mostafadevo.marvelous.data.remote.MarvelApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MarvelRepository @Inject constructor(
    private val api: MarvelApi,
    private val dao : MarvelDao
) {
    fun getCharacters(): Flow<List<CharacterEntity>> = flow {
        val cachedCharacters = dao.getCharacters().first()
        if (cachedCharacters.isNotEmpty()) {
            Log.d("CharacterRepository", "Loaded ${cachedCharacters.size} characters from cache.")
            emit(cachedCharacters)
        }

        try {
            val response = api.getCharacters()
            if (response.isSuccessful) {
                response.body()?.data?.charactersDTOS?.let { charactersDto ->
                    val characterEntities = charactersDto.map { dto ->
                        dto.toEntity()
                    }
                    dao.insertAll(characterEntities)
                    emit(characterEntities)
                } ?: run {
                    Log.e("CharacterRepository", "No characters found in API response.")
                }
            } else {
                emit(cachedCharacters)
            }
        } catch (e: Exception) {
            Log.e("CharacterRepository", "Error fetching characters: ${e.message}")
            emit(cachedCharacters)
        }
    }
}