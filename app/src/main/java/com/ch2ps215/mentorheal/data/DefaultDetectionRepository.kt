package com.ch2ps215.mentorheal.data

import com.ch2ps215.mentorheal.data.local.DetectionLocalDataSource
import com.ch2ps215.mentorheal.data.local.entity.FormEntity
import com.ch2ps215.mentorheal.data.mapper.asModel
import com.ch2ps215.mentorheal.data.remote.DetectionRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.TfLiteUserClassifierDataSource
import com.ch2ps215.mentorheal.data.remote.payload.SaveDetectionExpressionRequest
import com.ch2ps215.mentorheal.data.remote.payload.SaveDetectionFormRequest
import com.ch2ps215.mentorheal.domain.model.Form
import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.ch2ps215.mentorheal.domain.repository.DetectionRepository
import com.google.firebase.firestore.Query
import java.io.File

class DefaultDetectionRepository(
    private val detectionLocalDataSource: DetectionLocalDataSource,
    private val detectionRemoteDataSource: DetectionRemoteDataSource,
    private val tfLiteUserClassifierDataSource: TfLiteUserClassifierDataSource
) : DetectionRepository {
    override suspend fun getDetections(idUser: String): Query {
        return detectionRemoteDataSource.getDetections(idUser)
    }

    override suspend fun detectForm(
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
    ): FormDetection {
        val detect = Form(
            umur = umur,
            gender = gender,
            bidang = bidang,
            semester = semester,
            cgpa = cgpa,
            pernikahan = pernikahan,
            depresi = depresi,
            kecemasan = kecemasan,
            panic = panic,
            kebutuhanKhusus = kebutuhankhusus,
            userId = userId
        )

        val res = detectionRemoteDataSource.detectForm(detect)
        return res.asModel(userId)
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

    private fun FormEntity.toModel(): Form {
        return Form(
            umur = umur,
            gender = gender,
            bidang = bidang,
            semester = semester,
            cgpa = cgpa,
            pernikahan = pernikahan,
            depresi = depresi,
            kecemasan = kecemasan,
            panic = panic,
            kebutuhanKhusus = kebutuhanKhusus,
            userId = userId
        )
    }

    override suspend fun save(
        token: String,
        label: String,
        scores: Float,
        idUser: String
    ): Boolean {
        val req = SaveDetectionFormRequest(
            id = "",
            label = label,
            scores = scores,
            idUser = idUser
        )

        return detectionRemoteDataSource.saveForm(req)
    }

    override suspend fun update(token: String, detectionId: Int, total: Int): FormDetection {
        TODO("Not yet implemented")
    }

    override suspend fun delete(token: String, detectionId: Int): FormDetection {
        TODO("Not yet implemented")
    }
}
