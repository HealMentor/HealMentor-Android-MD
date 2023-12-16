package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.repository.ArticleRepository
import kotlinx.coroutines.tasks.await

class GetArticleUseCase(
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(
        int: String
    ): Result<Article> = runCatching {
        articleRepository.getArticleById(int).get().await().toObject(Article::class.java)!!
    }
}
