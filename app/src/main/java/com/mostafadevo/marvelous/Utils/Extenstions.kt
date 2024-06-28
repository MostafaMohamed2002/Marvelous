package com.mostafadevo.marvelous.Utils

import com.mostafadevo.marvelous.data.local.CharacterEntity
import com.mostafadevo.marvelous.data.remote.dto.CharacterDTO
import java.security.MessageDigest

fun String.toMd5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}

//characterdto to entity
fun CharacterDTO.toEntity(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        thumbnail = this.thumbnail.path + "." + this.thumbnail.extension
    )
}