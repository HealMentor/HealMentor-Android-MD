package com.ch2ps215.mentorheal.data.local.entity

data class UserEntity(
    val id: String,
    val name: String,
    val email: String,
    val photo: String?,
    val token: String
)
