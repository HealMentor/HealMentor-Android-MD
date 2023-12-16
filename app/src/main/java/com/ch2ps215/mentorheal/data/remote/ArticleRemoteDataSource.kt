package com.ch2ps215.mentorheal.data.remote

import com.ch2ps215.mentorheal.data.remote.payload.ArticleResponse
import com.ch2ps215.mentorheal.data.remote.service.ArticleService
import com.google.firebase.firestore.CollectionReference

class ArticleRemoteDataSource(
    private val articleRef: CollectionReference,
    private val articleService: ArticleService
) {
    fun getArticles() = articleRef.limit(20)
    fun getArticleById(id: String) = articleRef.document(id)
    fun getArticles(category: String) = articleRef
        .whereEqualTo("category", category)
        .limit(20)

    suspend fun getFavoriteArticles(token: String): List<ArticleResponse> {
        val res = articleService.getFavoriteArticles("Bearer $token")
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun like(token: String, articleId: Int): ArticleResponse {
        val res = articleService.like("Bearer $token", articleId)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun unlike(token: String, articleId: Int): ArticleResponse {
        val res = articleService.unlike("Bearer $token", articleId)
        val data = res.takeIf { it.isSuccessful }?.body()?.data
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }
}
