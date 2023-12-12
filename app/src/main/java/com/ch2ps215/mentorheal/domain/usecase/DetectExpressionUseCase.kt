package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.model.ExpressionDetection
import com.ch2ps215.mentorheal.domain.repository.DetectionRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import java.io.File

class DetectExpressionUseCase(
    private val userRepository: UserRepository,
    private val detectionRepository: DetectionRepository
) {

    suspend operator fun invoke(file: File) = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw Exception("Unauthorized")
        detectionRepository.detectExpression(user.id, file)
    }
}
