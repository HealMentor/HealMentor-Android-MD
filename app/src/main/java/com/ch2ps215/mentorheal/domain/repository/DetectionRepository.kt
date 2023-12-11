package com.ch2ps215.mentorheal.domain.repository

import com.ch2ps215.mentorheal.domain.model.Detection
import com.google.firebase.firestore.Query

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
    ): Detection

    suspend fun save(token: String, label: String, scores: Float, idUser: String): Boolean

    suspend fun update(token: String, detectionId: Int, total: Int): Detection

    suspend fun delete(token: String, detectionId: Int): Detection
}
