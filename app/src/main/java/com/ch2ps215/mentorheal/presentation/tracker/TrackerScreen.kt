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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ch2ps215.mentorheal.presentation.common.component.TopAppBar
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import com.ch2ps215.mentorheal.presentation.tracker.component.SearchWithAddButton
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TrackerScreen(
    navController: NavHostController,
    viewModel: TrackerViewModel = hiltViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackBarHostState::showSnackbar)
    }

    TrackerScreen(
        snackBarHostState = snackBarHostState,
    )
}

@Composable
fun TrackerScreen(
    snackBarHostState: SnackbarHostState,
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Tracker",
                onNavigationBack = { navController.popBackStack() }
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
            Text(text = "Kecemasan", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "This is a sample description for the Task Screen.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchWithAddButton(
                onSearchClick = {
                    // Aksi untuk klik pencarian
                },
                onAddClick = {
                    // Aksi untuk klik tambah
                }
            )

        }
    }
}

@Preview
@Composable
fun TrackerScreenPreview() {
    MentorhealTheme {
        TrackerScreen(
            snackBarHostState = remember { SnackbarHostState() }
        )
    }
}
