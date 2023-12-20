package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.repository.ArticleRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.tasks.await

class GetFavoriteArticlesUseCase(
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(): Result<Query> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw Exception("Unauthorized")
        articleRepository.getFavoriteArticle(user.id)
    }

    suspend operator fun invoke(idArticle:String): Result<Boolean> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw Exception("Unauthorized")
        articleRepository.isArticleFavorite(idArticle, user.id).get().await().exists()
    }
}
