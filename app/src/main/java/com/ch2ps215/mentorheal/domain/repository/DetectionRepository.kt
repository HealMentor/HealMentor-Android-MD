package com.ch2ps215.mentorheal.domain.repository

import com.ch2ps215.mentorheal.domain.model.Detection
import kotlinx.coroutines.flow.Flow

interface DetectionRepository {

    suspend fun getDetections(token: String): Flow<List<Detection>>

    suspend fun getDetection(token: String, detectionId: Int): Detection

    suspend fun save(token: String, label: String, scores: Float, idUser: String): Boolean

    suspend fun delete(token: String, detectionId: Int): Detection
}
