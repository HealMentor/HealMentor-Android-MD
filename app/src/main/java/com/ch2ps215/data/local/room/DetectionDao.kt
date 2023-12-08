package com.ch2ps215.data.local.room

import androidx.room.*
import com.ch2ps215.data.local.entity.DetectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DetectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(detection: DetectionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(detections: List<DetectionEntity>)

    @Query("SELECT * FROM detection ORDER BY created_at DESC")
    fun getDetections(): Flow<List<DetectionEntity>>

    @Delete
    suspend fun delete(detection: DetectionEntity)

    @Query("DELETE FROM detection")
    suspend fun delete()
}
