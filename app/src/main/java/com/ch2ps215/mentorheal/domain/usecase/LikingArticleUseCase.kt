package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.repository.ArticleRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class LikingArticleUseCase(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(articleId: Int, liked: Boolean = true): Result<Article> =
        runCatching {
            val user = userRepository
                .getUser()
                .firstOrNull() ?: throw RuntimeException("Unauthorized")
            if (liked) articleRepository.like(user.token, articleId)
            else articleRepository.unlike(user.token, articleId)
        }
}
