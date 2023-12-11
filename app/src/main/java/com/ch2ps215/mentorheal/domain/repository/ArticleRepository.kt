package com.ch2ps215.mentorheal.domain.repository

import com.ch2ps215.mentorheal.domain.model.Article

interface ArticleRepository {

    suspend fun getArticles(
        category: String?,
        garbageCategory: String?,
        page: Int,
        size: Int
    ): List<Article>

    suspend fun getFavoriteArticle(token: String): List<Article>

    suspend fun like(token: String, articleId: Int): Article

    suspend fun unlike(token: String, articleId: Int): Article
}
