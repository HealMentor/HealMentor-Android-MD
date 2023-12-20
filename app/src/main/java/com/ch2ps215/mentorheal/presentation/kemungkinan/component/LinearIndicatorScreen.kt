package com.ch2ps215.mentorheal.presentation.kemungkinan.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LinearIndicatorScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                rating.items?.let { Ratings(percentage = 60f) }
            }
        }
    }
}


@Composable
fun Ratings(percentage: Float) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            // Menggunakan nilai persentase yang diberikan
            LinearProgressIndicator(
                progress = percentage / 100f,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(5.dp))
                    .height(7.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
            )
        }
    }
}

@Preview
@Composable
fun LinearIndicatorScreenPreview() {
    MentorhealTheme {
        LinearIndicatorScreen()
    }
}

