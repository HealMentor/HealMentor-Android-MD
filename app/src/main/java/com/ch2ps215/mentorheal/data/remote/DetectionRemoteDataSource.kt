package com.ch2ps215.mentorheal.data.remote

import androidx.core.net.toUri
import com.ch2ps215.mentorheal.core.Constants.FORM_DETECTION_RESULT
import com.ch2ps215.mentorheal.data.remote.payload.DetectFormRequest
import com.ch2ps215.mentorheal.data.remote.payload.FormDetectionResponse
import com.ch2ps215.mentorheal.data.remote.payload.SaveDetectionExpressionRequest
import com.ch2ps215.mentorheal.data.remote.service.DetectionService
import com.ch2ps215.mentorheal.data.remote.service.FormService
import com.ch2ps215.mentorheal.domain.model.ExpressionDetection
import com.ch2ps215.mentorheal.domain.model.Form
import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.File

class DetectionRemoteDataSource(
    private val detectionFormRef: CollectionReference,
    private val detectionExpressionRef: CollectionReference,
    private val firebaseStorage: FirebaseStorage,
    private val detectionService: DetectionService,
    private val formService: FormService
) {
    fun getForm(idUser: String) = detectionFormRef
        .whereEqualTo("idUser", idUser)
        .limit(10)

    fun getFormDetectionsResult(idForm: String) = detectionFormRef
        .document(idForm).collection(FORM_DETECTION_RESULT).limit(20)

    fun getDetectionExpression(idUser: String) = detectionExpressionRef
        .whereEqualTo("idUser", idUser)
        .limit(10)

    suspend fun saveForm(req: Form): String {
        val id = detectionFormRef.document().id
        detectionFormRef.document(id).set(req.copy(id = id)).await()
        return id
    }

    suspend fun saveFormDetections(idForm: String, req: FormDetection) {
        detectionFormRef.document(idForm).collection(FORM_DETECTION_RESULT).document().set(req)
            .await()
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

    suspend fun detectForm(form: DetectFormRequest): FormDetectionResponse {
        val res = formService.detect(form)
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
