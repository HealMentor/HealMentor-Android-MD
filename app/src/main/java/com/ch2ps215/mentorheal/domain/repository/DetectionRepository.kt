package com.ch2ps215.mentorheal.domain.repository

import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import java.io.File

interface DetectionRepository {

    suspend fun getForm(idUser: String): Query

    suspend fun getFormDetectionsResult(idForm: String): Query

    suspend fun getDetectionExpression(idUser: String): Query

    suspend fun detectForm(
        age: Int,
        gender: String,
        major: String,
        semester: Int,
        cgpa: Int,
        marriage: String,
        depression: String,
        anxiety: String,
        panic: String,
        treatment: String,
        userId: String
    ): String

    suspend fun detectExpression(idUser: String, file: File)

    suspend fun update(token: String, detectionId: Int, total: Int): FormDetection

    suspend fun delete(token: String, detectionId: Int): FormDetection
}
