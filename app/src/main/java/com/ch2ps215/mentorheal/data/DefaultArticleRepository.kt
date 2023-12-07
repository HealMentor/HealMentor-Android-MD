package com.ch2ps215.mentorheal.data

import com.ch2ps215.mentorheal.data.mapper.asModel
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.repository.ArticleRepository

class DefaultArticleRepository(
) : ArticleRepository {

    override suspend fun getArticles(
        category: String?,
        garbageCategory: String?,
        page: Int,
        size: Int
    ): List<Article> {
        TODO("Not yet implemented")
    }

    override suspend fun getArticle(token: String, articleId: Int): Article {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteArticle(token: String): List<Article> {
        TODO("Not yet implemented")
    }

    override suspend fun like(token: String, articleId: Int): Article {
        TODO("Not yet implemented")
    }

    override suspend fun unlike(token: String, articleId: Int): Article {
        TODO("Not yet implemented")
    }
}
