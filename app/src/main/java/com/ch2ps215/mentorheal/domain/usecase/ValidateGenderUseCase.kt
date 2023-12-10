package com.ch2ps215.mentorheal.domain.usecase

import androidx.annotation.StringRes

class ValidateGenderUseCase(
    /**
     * [Int] reference to string resource.
     * You can use string resource to inject these values
     * */
    @StringRes private val errorBlankMessage: Int,
) {

    operator fun invoke(gender: String): Int? {
        if (gender.isBlank()) return errorBlankMessage

        return null
    }
}
