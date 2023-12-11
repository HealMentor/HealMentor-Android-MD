package com.ch2ps215.mentorheal.data.remote

import com.ch2ps215.mentorheal.data.remote.payload.DetectionResponse
import com.ch2ps215.mentorheal.data.remote.payload.SaveDetectionRequest
import com.ch2ps215.mentorheal.data.remote.service.DetectionService
import com.ch2ps215.mentorheal.domain.model.Form
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

class DetectionRemoteDataSource(
    private val detectionRef: CollectionReference,
    private val detectionService: DetectionService
) {
    fun getDetections(idUser: String) = detectionRef
        .whereEqualTo("idUser", idUser)
        .limit(10)

    suspend fun save(req: SaveDetectionRequest): Boolean {
        val id = detectionRef.document().id
        detectionRef.document(id).set(req.copy(id = id)).await()
        return true
    }

    suspend fun detectForm(form: Form): DetectionResponse {
        val res = detectionService.detect(form)
        val data = res.takeIf { it.isSuccessful }?.body()
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }
}
