package com.ch2ps215.mentorheal.presentation.twos.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.core.Constants.IMAGE_BASE_URL
import com.ch2ps215.mentorheal.domain.model.ExpressionDetection
import com.ch2ps215.mentorheal.domain.model.toExpression
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme

@Composable
fun DetectionCardExpression(
    modifier: Modifier = Modifier,
    detection: ExpressionDetection,
    onClick: (ExpressionDetection) -> Unit = { }
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = modifier
                .clickable(onClick = { onClick(detection) })
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("$IMAGE_BASE_URL${detection.image}?alt=media")
                    .placeholder(R.drawable.register)
                    .crossfade(400)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Box {
                Text(
                    text = "\n\n",
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterStart),
                    text = detection.label?.toExpression() ?: "Unknown",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetectionCardExpressionPreview() {
    MentorhealTheme {
        DetectionCardExpression(
            detection = ExpressionDetection(
                id = "1",
                image = "https://i.imgur.com/2xZJYzE.jpg",
                label = "Happy"
            )
        )
    }
}
