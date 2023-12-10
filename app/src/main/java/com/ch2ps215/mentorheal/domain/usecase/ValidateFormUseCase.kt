package com.ch2ps215.mentorheal.domain.usecase

import androidx.annotation.StringRes

class
ValidateFormUseCase(
    /**
     * [Int] reference to string resource.
     * You can use string resource to inject these values
     * */
    @StringRes private val errorBlankMessage: Int,
) {

    operator fun invoke(form: String): Int? {
        if (form.isBlank()) return errorBlankMessage

        return null
    }
}
