// AddTrackerScreen.kt

package com.ch2ps215.mentorheal.presentation.tracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.data.local.entity.TrackerEntity
import com.ch2ps215.mentorheal.presentation.common.component.TopAppBar
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import com.ch2ps215.mentorheal.presentation.signin.SignInViewModel
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AddTrackerScreen(
    navController: NavHostController,
    viewModel: TrackerViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    AddTrackerScreenview(
        snackbarHostState = snackbarHostState,
        onChangeTitle = viewModel::changeTitle,
        onChangeStar = viewModel::changeStar,
        onChangeDescription = viewModel::changeDescription,
        titleFieldState = viewModel.titleField,
        starFieldState = viewModel.starCount,
        descriptionFieldState = viewModel.description,
        loadingState = viewModel.loading,
        onClickSubmit = viewModel::save,
        onNavigateToTracker = {
            navController.navigate(Route.Tracker())
        },
    )
}
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddTrackerScreenview(
    snackbarHostState: SnackbarHostState,
    onChangeTitle: (String) -> Unit,
    onChangeStar: (String) -> Unit,
    onChangeDescription: (String) -> Unit,
    titleFieldState: StateFlow<Pair<String, Int?>>,
    starFieldState: StateFlow<Pair<String, Int?>>,
    descriptionFieldState: StateFlow<Pair<String, Int?>>,
    loadingState: StateFlow<Boolean>,
    onClickSubmit: () -> Unit,
    onNavigateToTracker: () -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current.density
    val keyboardController = LocalSoftwareKeyboardController.current
    val persaanOptions = listOf("Good", "Great", "Bad", "Biasa Saja")

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            androidx.compose.material3.TopAppBar(title = { Text(text = "Tracker") })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
//            var title by remember { mutableStateOf("") }
            var selectedPerasaan by remember { mutableStateOf(persaanOptions[0]) }
//            var deskripsi by remember { mutableStateOf("") }
            var rating by remember { mutableStateOf(1) }

            val titleField by titleFieldState.collectAsState()
            val (title, titleError) = titleField

            OutlinedTextField(
                value = title,
                onValueChange = onChangeTitle,
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Dropdown for perasaan
            DropdownMenu(
                expanded = false, // Initial state
                onDismissRequest = { /* Dismiss the menu */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                persaanOptions.forEach { perasaan ->
                    androidx.compose.material.DropdownMenuItem(onClick = {
                        selectedPerasaan = perasaan
                    }) {
                        Text(perasaan)
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        // Toggle the dropdown menu
                        // You can handle this based on your UI requirements
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = selectedPerasaan)
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }

            val deskripsiField by descriptionFieldState.collectAsState()
            val (deskripsi, deskipsiErro) = deskripsiField

            OutlinedTextField(
                value = deskripsi,
                onValueChange = onChangeDescription,
                label = { Text("Deskripsi") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(120.dp)
            )

            val starField by starFieldState.collectAsState()
            val (star, starError) = starField
//            var starrating = star.toInt()

            // Rating bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                (1..5).forEach { i ->
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (i <= rating) colorResource(id = R.color.yellow) else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                rating = i
                                onChangeStar(i.toString())
                            }
                    )
                }
            }

            // Button to submit data (you can customize the action)
            Button(
                onClick = onClickSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Submit")
            }
        }
    }
}

@Preview
@Composable
fun AddTrackerScreenPreview() {
    val navController = rememberNavController()
    MentorhealTheme {
        AddTrackerScreen(
            navController = navController
        )
    }
}
