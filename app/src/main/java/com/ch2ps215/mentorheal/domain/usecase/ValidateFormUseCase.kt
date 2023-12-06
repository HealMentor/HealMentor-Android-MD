package com.ch2ps215.mentorheal.domain.usecase

import androidx.annotation.StringRes

class
ValidateFormUseCase(
    /**
     * [Int] reference to string resource.
     * You can use string resource to inject these values
     * */
    @StringRes private val errorBlankMessage: Int,
    @StringRes private val errorMinMessage: Int,
    @StringRes private val errorMaxMessage: Int,
) {

    operator fun invoke(name: String): Int? {
        if (name.isBlank()) return errorBlankMessage

        if (name.length < 3) return errorMinMessage

        if (name.length > 50) return errorMaxMessage

        return null
    }
}
