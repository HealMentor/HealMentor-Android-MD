package com.ch2ps215.mentorheal.data

import com.ch2ps215.mentorheal.data.remote.ArticleRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.payload.DeleteArticleLikesRequest
import com.ch2ps215.mentorheal.data.remote.payload.SaveArticleLikesRequest
import com.ch2ps215.mentorheal.domain.repository.ArticleRepository
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import java.sql.Timestamp
import java.util.Date

class DefaultArticleRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {
    override suspend fun getArticles(): Query {
        return articleRemoteDataSource.getArticles()
    }

    override suspend fun getArticles(
        category: String
    ): Query {
        return articleRemoteDataSource.getArticles(category)
    }

    override suspend fun getArticleById(id: String): DocumentReference {
        return articleRemoteDataSource.getArticleById(id)
    }

    override suspend fun getFavoriteArticle(userId: String): Query {
        return articleRemoteDataSource.getFavoriteArticles(userId)
    }

    override suspend fun isArticleFavorite(idArticle: String, userId: String): DocumentReference {
        return articleRemoteDataSource.isArticleFavorite(idArticle, userId)
    }

    override suspend fun like(articleId: String, userId: String) {
        val req = SaveArticleLikesRequest(
            id = articleId,
            timestamp = Timestamp(Date().time),
            userId = userId
        )

        return articleRemoteDataSource.like(req)
    }

    override suspend fun unlike(articleId: String, userId: String) {
        val req = DeleteArticleLikesRequest(
            id = articleId,
            userId = userId
        )

        return articleRemoteDataSource.unLike(req)
    }
}
