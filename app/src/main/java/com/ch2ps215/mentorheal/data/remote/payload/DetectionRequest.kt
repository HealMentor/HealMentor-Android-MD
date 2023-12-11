package com.ch2ps215.mentorheal.data.remote.payload

data class DetectionRequest(

    val anxiety: String,
    val treatment: String,
    val gender: String,
    val major: String,
    val year: Int,
    val marriage: String,
    val cgpa: Int,
    val age: Int,
    val panic: String
)
