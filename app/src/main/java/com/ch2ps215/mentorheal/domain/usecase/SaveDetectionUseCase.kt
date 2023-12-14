package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.repository.DetectionRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class SaveDetectionUseCase constructor(
    private val userRepository: UserRepository,
    private val detectionRepository: DetectionRepository
) {

    suspend operator fun invoke(
        label: String,
        score: Float,
    ): Result<Boolean> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw RuntimeException("Unauthorized")
        detectionRepository.save(user.token, label, score.toInt(), user.id)
    }
}
