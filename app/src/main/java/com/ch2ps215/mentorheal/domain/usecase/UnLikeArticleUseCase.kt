package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.repository.ArticleRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class UnLikeArticleUseCase(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(articleId: String): Result<Unit> =
        runCatching {
            val user = userRepository
                .getUser()
                .firstOrNull() ?: throw RuntimeException("Unauthorized")
            articleRepository.unlike(articleId, user.id)
        }
}
