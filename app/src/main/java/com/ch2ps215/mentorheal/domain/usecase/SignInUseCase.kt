package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.model.User
import com.ch2ps215.mentorheal.domain.repository.UserRepository

class SignInUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(email: String, password: String): Result<User> = runCatching {
        userRepository.signIn(email, password)
    }
}
