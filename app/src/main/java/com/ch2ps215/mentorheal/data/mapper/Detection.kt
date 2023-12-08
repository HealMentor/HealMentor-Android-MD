package com.ch2ps215.mentorheal.data.mapper

import com.ch2ps215.mentorheal.data.local.entity.DetectionEntity
import com.ch2ps215.mentorheal.domain.model.Detection

fun Detection.asEntity(): DetectionEntity {
    return DetectionEntity(
        id = id,
        label = label,
        scores = scores,
        idUser = idUser,
        createdAt = createdAt
    )
}
fun DetectionEntity.asModel(): Detection {
    return Detection(
        id = id,
        label = label,
        scores = scores,
        idUser = idUser,
        createdAt = createdAt
    )
}
