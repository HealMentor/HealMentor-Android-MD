package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.repository.ArticleRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class GetFavoriteArticlesUseCase(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(): Result<List<Article>> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw Exception("Unauthorized")
        articleRepository.getFavoriteArticle(user.token)
    }
}
