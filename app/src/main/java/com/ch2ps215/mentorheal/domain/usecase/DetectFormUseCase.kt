package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.ch2ps215.mentorheal.domain.repository.DetectionRepository

class DetectFormUseCase(
    private val formRepository: DetectionRepository
) {
    suspend operator fun invoke(
        umur: String,
        gender: String,
        bidang: String,
        semester: String,
        cgpa: String,
        pernikahan: String,
        depresi: String,
        kecemasan: String,
        panic: String,
        kebutuhanKhusus: String,
        userId: String
    ): Result<FormDetection> = runCatching {
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
            userId
        )
    }
}