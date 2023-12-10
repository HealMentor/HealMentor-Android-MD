package com.ch2ps215.mentorheal.domain.usecase

import com.ch2ps215.mentorheal.domain.model.Form
import com.ch2ps215.mentorheal.domain.repository.IFormRepository

class FormUseCase(
    private val formRepository: IFormRepository
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
    ): Result<Form> = runCatching {
        formRepository.saveForm(
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