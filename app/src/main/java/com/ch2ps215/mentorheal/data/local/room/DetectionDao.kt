package com.ch2ps215.mentorheal.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ch2ps215.mentorheal.data.local.entity.DetectionEntity

@Dao
interface DetectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(detection: DetectionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(detections: List<DetectionEntity>)

    @Delete
    suspend fun delete(detection: DetectionEntity)

    @Query("DELETE FROM detection")
    suspend fun delete()
}
