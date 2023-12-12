package com.ch2ps215.mentorheal.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ch2ps215.mentorheal.data.local.entity.DetectionEntity
import com.ch2ps215.mentorheal.data.local.entity.FormEntity

@Database(
    version = 1,
    entities = [DetectionEntity::class, FormEntity::class],
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MentorHealDatabase : RoomDatabase() {

    abstract fun getDetectionDao(): DetectionDao
    abstract fun getFormDao(): FormDao
}
