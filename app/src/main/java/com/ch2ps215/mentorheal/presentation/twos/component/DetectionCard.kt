package com.ch2ps215.mentorheal.presentation.twos.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme

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
            val scores = formDetection.scores.toString()
                Text(
                    text = productName ?: "Unknown",
                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .weight(2f)
                        .padding(bottom = 4.dp)
                )
                Text(
                    text = scores ?: "Unknown",
                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 4.dp)
                )


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