package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class OnboardingUseCase(
    private val userRepository: UserRepository
) {

    operator fun invoke(): Flow<Boolean> {
        return userRepository.onboarding()
    }

    suspend operator fun invoke(isAlreadyOnboarding: Boolean) {
        userRepository.onboarding(isAlreadyOnboarding)
    }
}
