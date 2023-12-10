package com.ch2ps215.mentorheal.presentation.tracker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
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
import androidx.navigation.compose.rememberNavController
import com.ch2ps215.mentorheal.presentation.common.component.TopAppBar
import com.ch2ps215.mentorheal.presentation.kemungkinan.component.CardTracker
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import com.ch2ps215.mentorheal.presentation.tracker.component.Screen
import com.ch2ps215.mentorheal.presentation.tracker.component.SearchWithAddButton
import com.ch2ps215.mentorheal.presentation.tracker.component.TrackerItem
import kotlinx.coroutines.flow.collectLatest


@Composable
fun TrackerScreen(
    navController: NavHostController,
    viewModel: TrackerViewModel = hiltViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val trackerItems by viewModel.trackerItems.collectAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackBarHostState::showSnackbar)
    }

    TrackerScreen(
        snackBarHostState = snackBarHostState,
        trackerItems = trackerItems,
    )
}

@Composable
fun TrackerScreen(
    snackBarHostState: SnackbarHostState,
    trackerItems: List<TrackerItem>,
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Tracker",
                onClickNavigation = { navController.popBackStack() }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Proses Perkembangan Diri", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Catat Secara Mandiri Progres Perkembangan\n" +
                        "Kesehatan Anda",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchWithAddButton(
                onSearchClick = {
                    // Aksi untuk klik pencarian
                },
                onAddClick = {
                    navController.navigate(Screen.AddTracker.route)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Perkembangan Harian",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Create CardTracker for each item in the list
            trackerItems.forEach { item ->
                CardTracker(
                    title = item.title,
                    starCount = item.starCount,
                    onClick = {
                        navController.navigate("${Screen.Detail.route}/${item.title}/${item.description}/${item.starCount}")
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun TrackerScreenPreview() {
    val sampleData = listOf(
        TrackerItem("Good Feel", 3, "Feeling good today!"),
        TrackerItem("Great", 4, "Had a great day!"),
        TrackerItem("Bad", 1, "Not feeling well...")
    )

    MentorhealTheme {
        TrackerScreen(
            snackBarHostState = remember { SnackbarHostState() },
            trackerItems = sampleData
        )
    }
}