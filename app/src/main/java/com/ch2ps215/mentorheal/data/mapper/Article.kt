package com.ch2ps215.mentorheal.data.mapper

import com.ch2ps215.mentorheal.data.remote.payload.ArticleResponse
import com.ch2ps215.mentorheal.domain.model.Article

fun ArticleResponse.asModel(): Article {
    return Article(
        id = id,
        author = author,
        source = source,
        title = title,
        body = body,
        photo = photo,
        liked = liked,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
