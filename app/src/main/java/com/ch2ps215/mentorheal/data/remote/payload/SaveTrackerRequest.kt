package com.ch2ps215.mentorheal.data.remote.payload

data class SaveTrackerRequest(
    val id: String,
    val title: String,
    val starCount: Int,
    val description: String,
    val idUser: String
)
