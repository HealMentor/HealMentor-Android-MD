package com.ch2ps215.mentorheal.data.remote.payload

import java.sql.Timestamp

data class SaveArticleLikesRequest(
    val id: String,
    val timestamp: Timestamp,
    val userId: String,
)
