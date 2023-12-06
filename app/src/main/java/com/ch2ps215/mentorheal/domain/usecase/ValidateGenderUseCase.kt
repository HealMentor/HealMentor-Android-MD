package com.ch2ps215.mentorheal.domain.usecase

import androidx.annotation.StringRes

class ValidateGenderUseCase(
    /**
     * [Int] reference to string resource.
     * You can use string resource to inject these values
     * */
    @StringRes private val errorBlankMessage: Int,
    @StringRes private val errorInvalidMessage: Int,
) {

    operator fun invoke(gender: String): Int? {
        if (gender.isBlank()) return errorBlankMessage

        val validGenders = listOf("Male", "Female")
        if (!validGenders.contains(gender)) {
            return errorInvalidMessage
        }

        return null
    }
}
