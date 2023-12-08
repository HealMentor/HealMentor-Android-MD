package com.ch2ps215.mentorheal.data.local

import com.ch2ps215.mentorheal.data.local.entity.DetectionEntity
import com.ch2ps215.mentorheal.data.local.room.DetectionDao
import kotlinx.coroutines.flow.Flow
class DetectionLocalDataSource(
    private val detectionDao: DetectionDao
) {

    suspend fun save(detection: DetectionEntity) {
        detectionDao.save(detection)
    }

    suspend fun save(detections: List<DetectionEntity>) {
        detectionDao.save(detections)
    }

    fun getDetections(): Flow<List<DetectionEntity>> {
        return detectionDao.getDetections()
    }

    suspend fun delete(detectionEntity: DetectionEntity) {
        detectionDao.delete(detectionEntity)
    }

    suspend fun delete() {
        detectionDao.delete()
    }
}
