package com.ch2ps215.mentorheal.data

import com.ch2ps215.mentorheal.data.local.DetectionLocalDataSource
import com.ch2ps215.mentorheal.data.mapper.asEntity
import com.ch2ps215.mentorheal.data.mapper.asModel
import com.ch2ps215.mentorheal.data.remote.DetectionRemoteDataSource
import com.ch2ps215.mentorheal.data.remote.payload.SaveDetectionRequest
import com.ch2ps215.mentorheal.domain.model.Detection
import com.ch2ps215.mentorheal.domain.repository.DetectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultDetectionRepository(
    private val detectionLocalDataSource: DetectionLocalDataSource,
    private val detectionRemoteDataSource: DetectionRemoteDataSource
) : DetectionRepository {
    override suspend fun getDetections(token: String): Flow<List<Detection>> {
        val res = detectionRemoteDataSource.getDetections(token)
        detectionLocalDataSource.delete()
        detectionLocalDataSource.save(res.map { it.asEntity() })
        return detectionLocalDataSource
            .getDetections()
            .map { it.map { entity -> entity.asModel() } }
    }

    override suspend fun getDetection(token: String, detectionId: Int): Detection {
        return detectionRemoteDataSource.getDetection(token, detectionId)
    }

    override suspend fun save(token: String, label: String, scores: Float, idUser: String): Boolean {
        val req = SaveDetectionRequest(
            id = "",
            label = label,
            scores = scores,
            idUser = idUser
        )

        return detectionRemoteDataSource.save(req)
    }

    override suspend fun delete(token: String, detectionId: Int): Detection {
        return detectionRemoteDataSource.delete(token, detectionId)
    }
}
