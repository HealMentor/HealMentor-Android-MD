package com.ch2ps215.mentorheal.domain.repository

import com.ch2ps215.mentorheal.domain.model.Article
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query

interface ArticleRepository {

    suspend fun getArticles(): Query
    suspend fun getArticleById(id: String): DocumentReference
    suspend fun getArticles(category: String): Query

    suspend fun getFavoriteArticle(userId: String): Query

    suspend fun isArticleFavorite(idArticle: String, userId: String): DocumentReference

    suspend fun like(articleId: String, userId: String)

    suspend fun unlike(articleId: String, userId: String)
}
