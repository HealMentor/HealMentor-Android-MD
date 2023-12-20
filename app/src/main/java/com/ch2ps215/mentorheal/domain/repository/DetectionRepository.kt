package com.ch2ps215.mentorheal.domain.repository

import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.google.firebase.firestore.Query
import java.io.File

interface DetectionRepository {

    suspend fun getFormDetections(idUser: String): Query

    suspend fun getDetectionExpression(idUser: String): Query

    suspend fun detectForm(
        umur: Int,
        gender: String,
        bidang: String,
        semester: Int,
        cgpa: Int,
        pernikahan: String,
        depresi: String,
        kecemasan: String,
        panic: String,
        kebutuhankhusus: String,
        userId: String
    ): FormDetection

    suspend fun detectExpression(idUser: String, file: File)

    suspend fun save(token: String, label: String, scores: Int, idUser: String): Boolean

    suspend fun update(token: String, detectionId: Int, total: Int): FormDetection

    suspend fun delete(token: String, detectionId: Int): FormDetection
}
