package com.ch2ps215.mentorheal.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "detection")
data class DetectionEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val label: String,
    val scores: Float,
    val idUser: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: Date
)
