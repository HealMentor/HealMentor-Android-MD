package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.model.User
import com.ch2ps215.mentorheal.domain.repository.UserRepository

class SignUpUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ): Result<User> = runCatching {
        userRepository.signUp(name, email, password)
    }
}
