package com.ch2ps215.mentorheal.data

import com.ch2ps215.mentorheal.data.remote.ArticleRemoteDataSource
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.repository.ArticleRepository
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query

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

    override suspend fun getFavoriteArticle(token: String): List<Article> {
        TODO()
    }

    override suspend fun like(token: String, articleId: Int): Article {
        TODO()
    }

    override suspend fun unlike(token: String, articleId: Int): Article {
        TODO()
    }
}
