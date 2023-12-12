package com.ch2ps215.mentorheal.domain.repository

import android.graphics.Bitmap
import com.ch2ps215.mentorheal.domain.model.Classification
import java.io.File

interface UserClassification {
    fun classify(file: File): List<Classification>

    fun classify(bitmap: Bitmap, rotation: Int): List<Classification>
}