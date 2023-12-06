package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.repository.UserRepository

class SignOutUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke() = runCatching {
        userRepository.signOut()
    }
}
