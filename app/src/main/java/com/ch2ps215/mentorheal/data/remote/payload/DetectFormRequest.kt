package com.ch2ps215.mentorheal.data.remote.payload

import com.ch2ps215.mentorheal.domain.model.Form

data class DetectFormRequest(
    val gender: String,
    val age: Int,
    val major: String,
    val year: Int,
    val cgpa: Int,
    val marriage: String,
    val depression: String,
    val anxiety: String,
    val panic: String,
    val treatment: String,
)

fun DetectFormRequest.toForm(id: String, userId: String): Form {
    return Form(
        id = id,
        gender = gender,
        age = age,
        major = major,
        year = year,
        cgpa = cgpa,
        marriage = marriage,
        depression = depression,
        anxiety = anxiety,
        panic = panic,
        treatment = treatment,
        idUser = userId
    )
}
