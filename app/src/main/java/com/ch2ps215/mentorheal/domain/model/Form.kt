package com.ch2ps215.mentorheal.domain.model

data class Form(
    val id: Int = 0,
    val umur: String,
    val gender: String,
    val bidang: String,
    val semester: String,
    val cgpa: String,
    val pernikahan: String,
    val depresi: String,
    val kecemasan: String,
    val panic: String,
    val kebutuhanKhusus: String,
    val userId: String
)
