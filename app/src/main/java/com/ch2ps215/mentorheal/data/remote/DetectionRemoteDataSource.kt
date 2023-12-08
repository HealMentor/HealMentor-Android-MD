package com.ch2ps215.mentorheal.data.remote

import com.ch2ps215.mentorheal.data.remote.payload.SaveDetectionRequest
import com.ch2ps215.mentorheal.domain.model.Detection
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class DetectionRemoteDataSource(
    private val detectionRef: CollectionReference
) {

    suspend fun save(req: SaveDetectionRequest): Boolean {
        val id = detectionRef.document().id
        detectionRef.document(id).set(req.copy(id = id)).await()
        return true
    }

    suspend fun getDetections(token: String): List<Detection> {
        TODO()
    }

    suspend fun getDetection(token: String, id: Int): Detection {
        TODO()
    }

    suspend fun delete(token: String, id: Int): Detection {
        TODO()
    }
}
