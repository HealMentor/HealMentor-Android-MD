package com.ch2ps215.mentorheal.domain.repository

import com.ch2ps215.mentorheal.domain.model.Article
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query

interface ArticleRepository {

    suspend fun getArticles(): Query
    suspend fun getArticleById(id: String): DocumentReference
    suspend fun getArticles(category: String): Query

    suspend fun getFavoriteArticle(token: String): List<Article>

    suspend fun like(token: String, articleId: Int): Article

    suspend fun unlike(token: String, articleId: Int): Article
}
