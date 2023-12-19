package com.ch2ps215.mentorheal.domain.usecase

import androidx.annotation.StringRes
import androidx.core.util.PatternsCompat

class ValidateTitleUseCase(
    /**
     * [Int] reference to string resource.
     * You can use string resource to inject these values
     * */
    @StringRes private val errorBlankMessage: Int,
    @StringRes private val errorMinMessage: Int,
    @StringRes private val errorMaxMessage: Int,
) {

    operator fun invoke(title: String): Int? {
        if (title.isBlank()) return errorBlankMessage

        if (title.length < 3) return errorMinMessage

        if (title.length > 50) return errorMaxMessage

        return null
    }
}
