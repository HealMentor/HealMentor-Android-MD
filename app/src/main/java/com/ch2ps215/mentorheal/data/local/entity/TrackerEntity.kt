package com.ch2ps215.mentorheal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tracker"
)
data class TrackerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val starCount: Int,
    val description: String,
    val idUser: String,
)
