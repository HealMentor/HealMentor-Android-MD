package com.ch2ps215.data.remote.payload

import com.squareup.moshi.Json

data class UserResponse(
    @Json(name = "localId")
    val id: String,

    @Json(name = "displayName")
    val name: String? = null,

    @Json(name = "email")
    val email: String,

    @Json(name = "idToken")
    val token: String,

    @Json(name = "expiresIn")
    val expiresIn: String? = null,

    @Json(name = "kind")
    val kind: String? = null,

    @Json(name = "registered")
    val registered: Boolean? = null,

    @Json(name = "refreshToken")
    val refreshToken: String? = null
)
