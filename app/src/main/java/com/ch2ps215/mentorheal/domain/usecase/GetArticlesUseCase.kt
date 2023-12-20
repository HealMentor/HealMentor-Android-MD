package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.repository.ArticleRepository
import com.google.firebase.firestore.Query

class GetArticlesUseCase(
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(
    ): Result<Query> = runCatching {
        articleRepository.getArticles()
    }

    suspend operator fun invoke(
        category: String,
    ): Result<Query> = runCatching {
        articleRepository.getArticles(category)
    }
}
