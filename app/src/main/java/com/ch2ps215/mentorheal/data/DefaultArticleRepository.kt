package com.ch2ps215.mentorheal.data

import com.ch2ps215.mentorheal.data.remote.ArticleRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.payload.SaveDetectionRequest
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.repository.ArticleRepository

class DefaultArticleRepository(
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {

    suspend fun save(req: SaveDetectionRequest): Boolean {
        TODO()
    }

    override suspend fun getArticles(
        category: String?,
        garbageCategory: String?,
        page: Int,
        size: Int
    ): List<Article> {
        TODO()
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
