package com.ch2ps215.mentorheal.presentation.twos.component

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme

@SuppressLint("SuspiciousIndentation")
@Composable
fun DetectionCard(
    formDetection: FormDetection,
    onDetectionClick: (FormDetection) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
        elevation = 3.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp)
                .clickable {
                    onDetectionClick(formDetection)
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val productName = formDetection.label
            val scores = formDetection.scores?.toFloat() ?: 51F

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            ) {
                Text(
                    text = productName ?: "Unknown",
                    style = MaterialTheme.typography.h1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                )

                // Spacer untuk memberi jarak antara teks dan LinearProgressIndicator
                Spacer(modifier = Modifier.height(4.dp))

                LinearProgressIndicator(
                    progress = scores / 100F,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .height(7.dp),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.secondary.copy(
                        alpha = 0.3f
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun cardDetectionPreview() {
    val formDetection = FormDetection()
    MentorhealTheme {
        DetectionCard(formDetection = formDetection, onDetectionClick = {})
    }
}