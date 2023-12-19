package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.repository.TrackerRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.firstOrNull

class GetTrackerUseCase constructor(
    private val userRepository: UserRepository,
    private val trackerRepository: TrackerRepository
) {

    suspend operator fun invoke(): Result<Query> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw RuntimeException("Unauthorized")
        trackerRepository.getTracker(user.id)
    }
}
