package com.ch2ps215.mentorheal.domain.model

data class ExpressionDetection(
    val id: String? = null,
    val idUser: String? = null,
    val image: String? = null,
    val label: String? = null,
    val scores: Int? = null,
)

fun String.toExpression(): String {
    return when (this) {
        "0" -> "Anger"
        "1" -> "Contempt"
        "2" -> "Disgust"
        "3" -> "Fear"
        "4" -> "Happy"
        "5" -> "Sadness"
        "6" -> "Surprise"
        else -> "Unknown"
    }
}
