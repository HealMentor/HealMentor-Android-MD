package com.ch2ps215.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserEntity(
    val id: String,
    val name: String,
    val email: String,
    val photo: String?,
    val token: String
)
