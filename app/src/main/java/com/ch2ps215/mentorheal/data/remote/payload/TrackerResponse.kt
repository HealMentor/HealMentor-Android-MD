package com.ch2ps215.mentorheal.data.remote.payload

import com.google.gson.annotations.SerializedName
import java.util.*

data class TrackerResponse(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("starCount")
    val starCount: Int,
    @field:SerializedName("description")
    val description: String,
)