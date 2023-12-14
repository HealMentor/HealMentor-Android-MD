package com.ch2ps215.mentorheal.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tracker"
)
data class TrackerClass(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val feeling: String,
    val story: String,
    val rating: Int,
    val idUser: String,
)
