package com.ch2ps215.mentorheal.data

import com.ch2ps215.mentorheal.data.mapper.asModel
import com.ch2ps215.mentorheal.data.remote.ArticleRemoteDataSource
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.repository.ArticleRepository

class DefaultArticleRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {

    override suspend fun getArticles(
        category: String?,
        garbageCategory: String?,
        page: Int,
        size: Int
    ): List<Article> {
        val res = articleRemoteDataSource.getArticles(category, garbageCategory, page, size)
        return res.map { it.asModel() }
    }

    override suspend fun getArticle(token: String, articleId: Int): Article {
        val res = articleRemoteDataSource.getArticle(token, articleId)
        return res.asModel()
    }

    override suspend fun getFavoriteArticle(token: String): List<Article> {
        val res = articleRemoteDataSource.getFavoriteArticles(token)
        return res.map { it.asModel() }
    }

    override suspend fun like(token: String, articleId: Int): Article {
        val res = articleRemoteDataSource.like(token, articleId)
        return res.asModel()
    }

    override suspend fun unlike(token: String, articleId: Int): Article {
        val res = articleRemoteDataSource.unlike(token, articleId)
        return res.asModel()
    }
}
