package com.ch2ps215.mentorheal.data

import com.ch2ps215.mentorheal.data.local.DetectionLocalDataSource
import com.ch2ps215.mentorheal.data.remote.DetectionRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.TfLiteUserClassifierDataSource
import com.ch2ps215.mentorheal.data.remote.payload.DetectFormRequest
import com.ch2ps215.mentorheal.data.remote.payload.SaveDetectionExpressionRequest
import com.ch2ps215.mentorheal.data.remote.payload.toForm
import com.ch2ps215.mentorheal.data.remote.payload.toFormDetection
import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.ch2ps215.mentorheal.domain.repository.DetectionRepository
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import java.io.File

class DefaultDetectionRepository(
    private val detectionLocalDataSource: DetectionLocalDataSource,
    private val detectionRemoteDataSource: DetectionRemoteDataSource,
    private val tfLiteUserClassifierDataSource: TfLiteUserClassifierDataSource
) : DetectionRepository {
    override suspend fun getForm(idUser: String): Query {
        return detectionRemoteDataSource.getForm(idUser)
    }

    override suspend fun getFormDetectionsResult(idForm: String): Query {
        return detectionRemoteDataSource.getFormDetectionsResult(idForm)
    }

    override suspend fun getDetectionExpression(idUser: String): Query {
        return detectionRemoteDataSource.getDetectionExpression(idUser)
    }

    override suspend fun detectForm(
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
    ): String {
        val detect = DetectFormRequest(
            age = age,
            gender = gender,
            major = major,
            year = semester,
            cgpa = cgpa,
            marriage = marriage,
            depression = depression,
            anxiety = anxiety,
            panic = panic,
            treatment = treatment,
        )
        val apiRes = detectionRemoteDataSource.detectForm(detect)
        val idForm = detectionRemoteDataSource.saveForm(detect.toForm(id = "", userId = userId))
        detectionRemoteDataSource.saveFormDetections(
            idForm,
            apiRes.toFormDetection(userId = userId)
        )

        return idForm
    }

    override suspend fun detectExpression(idUser: String, file: File) {
        val result = tfLiteUserClassifierDataSource.classify(file).firstOrNull() ?: throw Exception(
            "Zero result, Model can't classify"
        )


        detectionRemoteDataSource.uploadImage(file)
        detectionRemoteDataSource.saveExpression(
            SaveDetectionExpressionRequest(
                id = "",
                label = result.label,
                scores = result.scores,
                image = file.name,
                idUser = idUser
            )
        )

    }

    override suspend fun update(token: String, detectionId: Int, total: Int): FormDetection {
        TODO("Not yet implemented")
    }

    override suspend fun delete(token: String, detectionId: Int): FormDetection {
        TODO("Not yet implemented")
    }
}
