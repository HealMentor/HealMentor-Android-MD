package com.ch2ps215.mentorheal.presentation.atasikecemasan


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
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AtasiKecemasanScreen(
    navController: NavHostController,
    viewModel: AtasiKecemasanViewModel = hiltViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackBarHostState::showSnackbar)
    }

    val title = "title"
    val description = "description"
    val steps = listOf<String>("1")


    AtasiKecemasanScreen(
        snackBarHostState = snackBarHostState,
        title = title,
        description = description,
        steps = steps,
        onNavigationBack = navController::popBackStack,
    )
}

@Composable
fun AtasiKecemasanScreen(
    snackBarHostState: SnackbarHostState,
    title: String,
    description: String,
    steps: List<String>,
    onNavigationBack: () -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Atasi Kecemasanmu",
                onNavigationBack = onNavigationBack
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
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Langkah-langkah:", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            steps.forEachIndexed { index, step ->
                Text(text = "${index + 1}. $step", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview
@Composable
fun AtasiKecemasanScreenPreview() {
    MentorhealTheme {
        AtasiKecemasanScreen(
            snackBarHostState = remember { SnackbarHostState() },
            title = "Kecemasan",
            description = "This is a sample description for the Task Screen.",
            steps = listOf(
                "Step 1: Do something",
                "Step 2: Do something else",
                "Step 3: Final step"
            ),
            onNavigationBack = {  }
        )
    }
}
