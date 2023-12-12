package com.ch2ps215.mentorheal.data.mapper

import com.ch2ps215.mentorheal.data.remote.payload.FormDetectionResponse
import com.ch2ps215.mentorheal.domain.model.FormDetection

fun FormDetectionResponse.asModel(idUser: String): FormDetection {
    return FormDetection(
        id = "",
        label = data?.depression ?: "",
        scores = data?.prediction ?: 0,
        idUser = idUser,
    )
}
