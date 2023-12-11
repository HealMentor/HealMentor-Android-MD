package com.ch2ps215.mentorheal.data.mapper

import com.ch2ps215.mentorheal.data.remote.payload.DetectionResponse
import com.ch2ps215.mentorheal.domain.model.Detection

fun DetectionResponse.asModel(idUser: String): Detection {
    return Detection(
        id = "",
        label = data?.depression ?: "",
        scores = data?.prediction ?: 0,
        idUser = idUser,
    )
}
