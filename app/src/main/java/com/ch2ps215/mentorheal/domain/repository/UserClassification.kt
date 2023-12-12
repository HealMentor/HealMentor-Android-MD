package com.ch2ps215.mentorheal.domain.repository

import android.graphics.Bitmap
import com.ch2ps215.mentorheal.domain.model.Classification

interface UserClassification {
    fun classify(bitmap: Bitmap, rotation: Int) : List<Classification>
}