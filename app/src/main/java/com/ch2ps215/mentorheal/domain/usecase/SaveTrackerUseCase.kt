package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.repository.TrackerRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class SaveTrackerUseCase constructor(
    private val userRepository: UserRepository,
    private val trackerRepository: TrackerRepository
) {

    suspend operator fun invoke(
        title: String,
        starCount: Float,
        description: String,
        feel: String
    ): Result<Boolean> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw RuntimeException("Unauthorized")
        trackerRepository.saveTracker( title, starCount.toInt(), description, feel, user.id)
    }
}
