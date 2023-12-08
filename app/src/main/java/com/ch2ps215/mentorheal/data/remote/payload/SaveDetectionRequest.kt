package com.ch2ps215.mentorheal.data.remote.payload
data class SaveDetectionRequest(
    val id: String,
    val label: String,
    val scores: Float,
    val idUser: String,
)
