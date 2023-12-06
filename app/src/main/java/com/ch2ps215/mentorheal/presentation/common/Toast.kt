package com.ch2ps215.mentorheal.presentation.common

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

@Suppress("NOTHING_TO_INLINE")
inline fun toast(context: Context, @StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, context.getString(message), duration).show()
}
