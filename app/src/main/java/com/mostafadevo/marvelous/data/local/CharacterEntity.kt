package com.mostafadevo.marvelous.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val thumbnail: String
)
