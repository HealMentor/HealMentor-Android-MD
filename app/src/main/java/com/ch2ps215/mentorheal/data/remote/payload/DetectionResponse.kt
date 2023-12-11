package com.ch2ps215.mentorheal.data.remote.payload

import com.squareup.moshi.Json

data class DetectionResponse(

    @Json(name = "data")
    val data: Data? = null,

    @Json(name = "status")
    val status: Status
)

data class Data(

    @Json(name = "prediction")
    val prediction: Int,

    @Json(name = "depression")
    val depression: String
)

data class Status(

    @Json(name = "code")
    val code: Int,

    @Json(name = "message")
    val message: String
)
