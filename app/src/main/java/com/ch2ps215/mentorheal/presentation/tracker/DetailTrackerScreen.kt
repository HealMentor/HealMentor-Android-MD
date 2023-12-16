package com.ch2ps215.mentorheal.presentation.tracker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ch2ps215.mentorheal.presentation.common.component.TopAppBar

@Composable
fun DetailTrackerScreen(
    navHostController: NavHostController
) {

    val navBackStackEntry = navHostController.currentBackStackEntryAsState()
    val arguments = navBackStackEntry.value

    val title = "Title"
    val description = "Deskription"
    val rating = 4
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Detail Tracker",
                onNavigationBack = navHostController::popBackStack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(text = "Title: $title", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Description: $description", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Rating: $rating", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    val navHostController = rememberNavController()
    DetailTrackerScreen(navHostController)
}
