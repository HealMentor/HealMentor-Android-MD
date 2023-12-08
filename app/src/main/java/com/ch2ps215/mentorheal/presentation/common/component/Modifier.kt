package com.ch2ps215.mentorheal.presentation.common.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

fun Modifier.shimmer(
    enabled: Boolean = true,
    shape: Shape? = null,
    startColor: Color = Color.White.copy(0.25F),
    endColor: Color = Color.Gray.copy(0.5F),
    duration: Int = 1000,
    drawBehind: Boolean = false
): Modifier = composed {
    if (!enabled) return@composed this

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val color by infiniteTransition.animateColor(
        initialValue = startColor,
        targetValue = endColor,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    if (drawBehind) {
        if (shape == null) {
            drawBehind { drawRect(color) }
        } else {
            clip(shape).drawBehind { drawRect(color) }
        }
    } else {
        if (shape == null) {
            drawWithContent { drawRect(color) }
        } else {
            clip(shape).drawWithContent { drawRect(color) }
        }
    }
}
