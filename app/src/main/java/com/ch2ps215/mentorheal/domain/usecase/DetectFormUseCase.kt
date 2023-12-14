package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.ch2ps215.mentorheal.domain.repository.DetectionRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class DetectFormUseCase(
    private val formRepository: DetectionRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        umur: Int,
        gender: String,
        bidang: String,
        semester: Int,
        cgpa: Int,
        pernikahan: String,
        depresi: String,
        kecemasan: String,
        panic: String,
        kebutuhanKhusus: String,
    ): Result<FormDetection> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw Exception("Unauthorized")
        formRepository.detectForm(
            umur,
            gender,
            bidang,
            semester,
            cgpa,
            pernikahan,
            depresi,
            kecemasan,
            panic,
            kebutuhanKhusus,
            user.id
        )
    }
}