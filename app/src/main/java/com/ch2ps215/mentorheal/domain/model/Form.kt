package com.ch2ps215.mentorheal.domain.model

import com.google.firebase.Timestamp
import java.util.Date

data class Form(
    val id: String = "",
    val gender: String? = null,
    val age: Int? = null,
    val major: String? = null,
    val year: Int? = null,
    val cgpa: Int? = null,
    val marriage: String? = null,
    val depression: String? = null,
    val anxiety: String? = null,
    val panic: String? = null,
    val treatment: String? = null,
    val timestamp: Timestamp = Timestamp(Date()),
    val idUser: String = ""
)
