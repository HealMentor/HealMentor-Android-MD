package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class DarkThemeUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(isDarkTheme: Boolean?) {
        userRepository.darkTheme(isDarkTheme)
    }

    operator fun invoke(): Flow<Boolean?> {
        return userRepository.darkTheme()
    }
}
