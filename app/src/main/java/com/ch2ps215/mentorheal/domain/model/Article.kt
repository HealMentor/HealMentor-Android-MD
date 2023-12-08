package com.ch2ps215.mentorheal.domain.model

import java.util.Date

data class Article(
    val id: Int,
    val author: String,
    val source: String,
    val title: String,
    val body: String,
    val photo: List<String>,
    val liked: Boolean,
    val createdAt: Date,
    val updatedAt: Date
)
