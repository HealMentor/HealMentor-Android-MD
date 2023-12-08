package com.ch2ps215.mentorheal.presentation.home.component

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
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme

@Composable
fun Article(
    modifier: Modifier = Modifier,
    id: Int,
    title: String,
    photo: String,
    onClick: (Int) -> Unit = { }
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = modifier.clickable(onClick = { onClick(id) })) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo)
                    .crossfade(500)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
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
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ArticlePlaceholder(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Text(
            text = "\n\n",
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlePreview() {
    MentorhealTheme {
        Article(
            id = 0,
            title = "lorem ipsum dolor sit amet",
            photo = "https://images.unsplash.com/photo-1621574539437-8b9b7b0b9b0f?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8dHJhc2hpbmd8ZW58MHx8MHx8&ixlib=rb-1.2.1&w=1000&q=80"
        )
    }
}
