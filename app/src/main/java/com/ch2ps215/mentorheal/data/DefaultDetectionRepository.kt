package com.ch2ps215.mentorheal.data

import com.ch2ps215.mentorheal.data.local.DetectionLocalDataSource
import com.ch2ps215.mentorheal.data.local.entity.FormEntity
import com.ch2ps215.mentorheal.data.mapper.asModel
import com.ch2ps215.mentorheal.data.remote.DetectionRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.payload.SaveDetectionRequest
import com.ch2ps215.mentorheal.domain.model.Detection
import com.ch2ps215.mentorheal.domain.model.Form
import com.ch2ps215.mentorheal.domain.repository.DetectionRepository
import com.google.firebase.firestore.Query

class DefaultDetectionRepository(
    private val detectionLocalDataSource: DetectionLocalDataSource,
    private val detectionRemoteDataSource: DetectionRemoteDataSource
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
    ): Detection {
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
        val req = SaveDetectionRequest(
            id = "",
            label = label,
            scores = scores,
            idUser = idUser
        )

        return detectionRemoteDataSource.save(req)
    }

    override suspend fun update(token: String, detectionId: Int, total: Int): Detection {
        TODO("Not yet implemented")
    }

    override suspend fun delete(token: String, detectionId: Int): Detection {
        TODO("Not yet implemented")
    }
}
