package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.repository.ArticleRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class LikingArticleUseCase(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(isLiked: Boolean, articleId: String): Result<Unit> =
        runCatching {
            val user = userRepository
                .getUser()
                .firstOrNull() ?: throw RuntimeException("Unauthorized")
            if (!isLiked) {
                articleRepository.like(articleId, user.id)
            } else {
                articleRepository.unlike(articleId, user.id)
            }
        }
}
