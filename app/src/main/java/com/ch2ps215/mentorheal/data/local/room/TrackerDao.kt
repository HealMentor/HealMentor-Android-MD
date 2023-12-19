package com.ch2ps215.mentorheal.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ch2ps215.mentorheal.data.local.entity.TrackerEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface TrackerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTracker(trackerEntity: TrackerEntity)

    @Query("SELECT * FROM tracker ORDER BY id DESC")
    fun getTracker(): Flow<List<TrackerEntity>>

}