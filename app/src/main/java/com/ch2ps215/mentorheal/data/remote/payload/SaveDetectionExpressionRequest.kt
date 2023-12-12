package com.ch2ps215.mentorheal.data.remote.payload

data class SaveDetectionExpressionRequest(
    val id: String,
    val label: String,
    val scores: Int,
    val image: String,
    val idUser: String,
)
