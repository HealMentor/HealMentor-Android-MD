package com.ch2ps215.mentorheal.domain.repository

import com.ch2ps215.mentorheal.domain.model.ExpressionDetection
import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.google.firebase.firestore.Query
import java.io.File

interface DetectionRepository {

    suspend fun getDetections(idUser: String): Query

    suspend fun detectForm(
        umur: String,
        gender: String,
        bidang: String,
        semester: String,
        cgpa: String,
        pernikahan: String,
        depresi: String,
        kecemasan: String,
        panic: String,
        kebutuhankhusus: String,
        userId: String
    ): FormDetection

    suspend fun detectExpression(idUser: String, file: File)

    suspend fun save(token: String, label: String, scores: Float, idUser: String): Boolean

    suspend fun update(token: String, detectionId: Int, total: Int): FormDetection

    suspend fun delete(token: String, detectionId: Int): FormDetection
}
