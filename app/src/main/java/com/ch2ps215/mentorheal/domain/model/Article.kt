package com.ch2ps215.mentorheal.domain.model

data class Article(
    val author: String? = null,
    val body: String? = null,
    val category: String? = null,
    val id: String? = null,
    val photo: List<String> = emptyList(),
    val source: String? = null,
    val title: String? = null,
)
