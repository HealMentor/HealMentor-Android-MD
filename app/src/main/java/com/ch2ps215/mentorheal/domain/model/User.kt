package com.ch2ps215.mentorheal.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val photo: String?,
    val token: String
)
