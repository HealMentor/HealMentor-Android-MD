package com.ch2ps215.data.mapper

import com.ch2ps215.data.local.entity.UserEntity
import com.ch2ps215.data.remote.payload.UserResponse
import com.ch2ps215.mentorheal.domain.model.User

fun UserResponse.asModel(): User {
    return User(
        id = id,
        name = name ?: "",
        email = email,
        photo = null,
        token = token
    )
}

fun UserResponse.asEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = name ?: "",
        email = email,
        photo = null,
        token = token
    )
}

fun UserEntity.asModel(): User {
    return User(
        id = id,
        name = name,
        email = email,
        photo = photo,
        token = token
    )
}
