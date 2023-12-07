package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.model.User
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class EditUserUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        name: String,
        password: String,
        photo: String? = null
    ): Result<User> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw RuntimeException("Unauthorized")
        userRepository.edit(user.token, name, password, photo)
    }
}
