package com.ch2ps215.mentorheal.presentation.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ch2ps215.mentorheal.R

@Composable
fun Photo(
    modifier: Modifier = Modifier,
    photo: String?,
    size: Dp = 120.dp,
    onClick: () -> Unit
) {
    AsyncImage(
        model = photo,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        fallback = painterResource(R.drawable.ic_profile_placeholder),
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(Color.Gray.copy(alpha = 0.5F))
            .clickable(onClick = onClick)
    )
}

@Preview(showBackground = true)
@Composable
fun PhotoPreview() {
    MaterialTheme {
        Photo(onClick = {}, photo = null)
    }
}
