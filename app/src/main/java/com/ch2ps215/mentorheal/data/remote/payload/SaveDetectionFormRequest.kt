package com.ch2ps215.mentorheal.data.remote.payload

data class SaveDetectionFormRequest(
    val id: String,
    val label: String,
    val scores: Int,
    val idUser: String,
)
