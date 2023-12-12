package com.ch2ps215.mentorheal.data.remote

import androidx.core.net.toUri
import com.ch2ps215.mentorheal.data.remote.payload.FormDetectionResponse
import com.ch2ps215.mentorheal.data.remote.payload.SaveDetectionExpressionRequest
import com.ch2ps215.mentorheal.data.remote.payload.SaveDetectionFormRequest
import com.ch2ps215.mentorheal.data.remote.service.DetectionService
import com.ch2ps215.mentorheal.domain.model.ExpressionDetection
import com.ch2ps215.mentorheal.domain.model.Form
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File
import javax.inject.Named

class DetectionRemoteDataSource (
    @Named("detectionsFormRef") private val detectionRef: CollectionReference,
    @Named("detectionsExpressionRef") private val detectionExpressionRef: CollectionReference,
    private val firebaseStorage: FirebaseStorage,
    private val detectionService: DetectionService
) {
    fun getDetections(idUser: String) = detectionRef
        .whereEqualTo("idUser", idUser)
        .limit(10)

    suspend fun saveForm(req: SaveDetectionFormRequest): Boolean {
        val id = detectionRef.document().id
        detectionRef.document(id).set(req.copy(id = id)).await()
        return true
    }

    suspend fun saveExpression(req: SaveDetectionExpressionRequest): Boolean {
        val id = detectionExpressionRef.document().id
        detectionExpressionRef.document(id).set(req.copy(id = id)).await()
        return true
    }

    suspend fun uploadImage(file: File): Boolean {
        val name = file.name
        firebaseStorage.reference.child("images/${name}").putFile(file.toUri()).await()
        return true
    }

    suspend fun detectForm(form: Form): FormDetectionResponse {
        val res = detectionService.detect(form)
        val data = res.takeIf { it.isSuccessful }?.body()
        if (data == null) {
            res.errorBody()?.let { throw RuntimeException(it.getErrorMessage()) }
        }

        return data ?: throw RuntimeException("Response body is empty")
    }

    suspend fun detectExpression(file: File): ExpressionDetection {
        TODO("Connect to Model through TF Lite")
    }
}
