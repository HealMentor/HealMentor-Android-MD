package com.ch2ps215.data.remote.payload

import com.squareup.moshi.Json

data class Api<T>(
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "data")
    val data: T
)
