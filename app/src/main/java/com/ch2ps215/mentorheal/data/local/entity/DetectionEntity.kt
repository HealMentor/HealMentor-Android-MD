package com.ch2ps215.mentorheal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detection")
data class DetectionEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val label: String,
    val scores: Int,
    val idUser: String,
)
