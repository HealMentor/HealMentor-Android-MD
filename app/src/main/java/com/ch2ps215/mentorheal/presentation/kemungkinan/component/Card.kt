package com.ch2ps215.mentorheal.presentation.kemungkinan.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch2ps215.mentorheal.R

@OptIn(ExperimentalTextApi::class)
@Composable
fun CardWithFavorite(
    title: String,
    description: String,
    onFavoriteToggle: () -> Unit,
    isFavorited: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFF4F4F4))
            .clip(RoundedCornerShape(16.dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }
                .padding(16.dp)
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
                Text(
                    text = description,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                val rating = 60.toFloat()
                LinearProgressIndicator(
                    progress = rating / 100F,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .height(7.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
                )
            }
            Column {
                FavoriteButton(onFavoriteToggle, isFavorited)
            }


        }

    }
}


@Composable
fun FavoriteButton(
    onFavoriteToggle: () -> Unit,
    isFavorited: Boolean
) {
    var isFavorite by remember { mutableStateOf(isFavorited) }

    Icon(
        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
        contentDescription = null,
        tint = colorResource(id = if (isFavorite) R.color.red else R.color.black),
        modifier = Modifier
            .size(24.dp)
            .clickable {
                isFavorite = !isFavorite
                onFavoriteToggle()
            }
    )
}

@Preview
@Composable
fun CardWithFavoritePreview() {
    CardWithFavorite(
        title = "Card Title",
        description = "This is a sample card description. You can replace it with your own content.",
        onFavoriteToggle = {},
        isFavorited = false,
        onClick = {}
    )
}
