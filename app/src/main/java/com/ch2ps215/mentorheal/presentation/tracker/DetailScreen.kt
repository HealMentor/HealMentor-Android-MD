package com.ch2ps215.mentorheal.presentation.tracker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(title: String, description: String, rating: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Title: $title", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Description: $description", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Rating: $rating", style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    DetailScreen("Good Feel", "Feeling good today!", 3)
}
