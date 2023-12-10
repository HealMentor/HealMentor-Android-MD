package com.ch2ps215.mentorheal.domain.model

import java.util.Date

data class Detection(
    val id: Int,
    val label: String,
    val scores: Float,
    val idUser: Int,
    val createdAt: Date
)
