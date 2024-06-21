package com.mostafadevo.marvelous.Utils

import java.security.MessageDigest

fun String.toMd5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}