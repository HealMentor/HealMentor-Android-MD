package com.ch2ps215.data.remote.payload

import com.squareup.moshi.Json

data class SignInRequest(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "returnSecureToken")
    val returnSecureToken: Boolean = true
)
