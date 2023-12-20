package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.repository.DetectionRepository
import com.ch2ps215.mentorheal.domain.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull

class DetectFormUseCase(
    private val formRepository: DetectionRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        age: Int,
        gender: String,
        major: String,
        semester: Int,
        cgpa: Int,
        marriage: String,
        depression: String,
        anxiety: String,
        panic: String,
        treatment: String,
    ): Result<String> = runCatching {
        val user = userRepository.getUser().firstOrNull() ?: throw Exception("Unauthorized")
        formRepository.detectForm(
            age = age,
            gender = gender,
            major = major,
            semester = semester,
            cgpa = cgpa,
            marriage = marriage,
            depression = depression,
            anxiety = anxiety,
            panic = panic,
            treatment = treatment,
            userId = user.id
        )
    }
}