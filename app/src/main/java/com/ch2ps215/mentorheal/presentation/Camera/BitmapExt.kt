package com.ch2ps215.mentorheal.presentation.Camera

import android.graphics.Bitmap
import java.lang.IllegalArgumentException

fun Bitmap.centerCrop(desiredWidth : Int, desiredHeigh: Int) :Bitmap {
    val xStart = (width - desiredWidth) / 2
    val yStart = (height - desiredHeigh) / 2

    if (xStart < 0 || yStart < 0 || desiredWidth > width || desiredHeigh > height) {
        throw IllegalArgumentException("invalid")
    }

    return Bitmap.createBitmap(this, xStart, yStart, desiredWidth, desiredHeigh)
}