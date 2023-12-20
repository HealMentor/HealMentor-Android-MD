package com.ch2ps215.mentorheal.data.local

import com.ch2ps215.mentorheal.data.local.entity.TrackerEntity
import com.ch2ps215.mentorheal.data.local.room.TrackerDao
import kotlinx.coroutines.flow.Flow

class TrackerLocalDataSource(
    private val trackerDao: TrackerDao
) {

    suspend fun saveTracker(tracker: TrackerEntity) {
        trackerDao.saveTracker(tracker)
    }

    fun getTracker(): Flow<List<TrackerEntity>> {
        return trackerDao.getTracker()
    }
}