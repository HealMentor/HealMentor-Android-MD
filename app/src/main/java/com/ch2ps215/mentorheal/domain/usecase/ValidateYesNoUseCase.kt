package com.ch2ps215.mentorheal.domain.usecase

import androidx.annotation.StringRes

class ValidateYesNoUseCase(
    /**
     * [Int] reference to string resource for the error message.
     * You can use string resource to inject these values
     * */
    @StringRes private val errorBlankMessage: Int,
    @StringRes private val errorInvalidMessage: Int,
) {

    operator fun invoke(yesno: String): Int? {
        if (yesno.isBlank()) return errorBlankMessage

        val validYesNo = listOf("Yes", "No")
        if (!validYesNo.contains(yesno)) {
            return errorInvalidMessage
        }

        return null
    }
}
