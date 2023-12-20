package com.ch2ps215.mentorheal.presentation.tracker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ch2ps215.mentorheal.domain.model.Tracker
import com.ch2ps215.mentorheal.presentation.common.component.TopAppBar
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailScreenTracker(
    navController: NavHostController,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    DetailScreenTrackerView(
        navHostController = navController,
        tracker = Tracker(),
    )
}
@Composable
fun DetailScreenTrackerView(
    navHostController: NavHostController,
    tracker: Tracker,
) {

    val navBackStackEntry = navHostController.currentBackStackEntryAsState()
    val arguments = navBackStackEntry.value

    val title = tracker.title
    val description = tracker.description
    val rating = tracker.starCount?.toInt() ?: 1
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
    val tracker = Tracker()
    val navHostController = rememberNavController()
    DetailScreenTracker(navHostController)
}
