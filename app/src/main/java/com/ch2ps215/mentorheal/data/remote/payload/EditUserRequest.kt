package com.ch2ps215.mentorheal.data.remote.payload

data class EditUserRequest(
    val name: String,
    val password: String,
    val photo: String?
)
