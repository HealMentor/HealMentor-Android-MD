package com.ch2ps215.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "form_keluhan"
)
data class FormEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val umur: String,
    val gender: String,
    val bidang: String,
    val semester: String,
    val cgpa: String,
    val pernikahan: String,
    val depresi: String,
    val kecemasan: String,
    val panic: String,
    val kebutuhanKhusus: String,
    @ColumnInfo(name = "userId") val userId: String
)
