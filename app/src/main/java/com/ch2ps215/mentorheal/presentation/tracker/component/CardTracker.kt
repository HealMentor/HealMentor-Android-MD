package com.ch2ps215.mentorheal.presentation.kemungkinan.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch2ps215.mentorheal.R

@Composable
fun CardTracker(
    title: String,
    starCount: Int,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFF24B0C1)) // Set the background color to blue
            .clip(RoundedCornerShape(16.dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }
                .background(Color(0xFF24B0C1)) // Set the background color to blue
                .clip(RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                )

            }
            Column {
                StarButton(starCount)
            }
        }
    }
}

@Composable
fun StarButton(
    starCount: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(starCount) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = colorResource(id = R.color.yellow),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun CardTrackerPreview() {
    CardTracker(
        title = "Card Title",
        starCount = 3,
        onClick = {}
    )
}
