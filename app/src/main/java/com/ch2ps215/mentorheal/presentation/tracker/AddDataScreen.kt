// AddTrackerScreen.kt

package com.ch2ps215.mentorheal.presentation.tracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import com.ch2ps215.mentorheal.presentation.tracker.component.DropDownMenuTracke
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
        onChangeFeel = viewModel::changeFeel,
        titleFieldState = viewModel.titleField,
        starFieldState = viewModel.starCount,
        descriptionFieldState = viewModel.description,
        feelFieldState = viewModel.feel,
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
    onChangeFeel: (String) -> Unit,
    titleFieldState: StateFlow<Pair<String, Int?>>,
    starFieldState: StateFlow<Pair<String, Int?>>,
    descriptionFieldState: StateFlow<Pair<String, Int?>>,
    feelFieldState: StateFlow<Pair<String, Int?>>,
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
//            var selectedPerasaan by remember { mutableStateOf(persaanOptions[0]) }
//            var deskripsi by remember { mutableStateOf("") }
            var rating by remember { mutableStateOf(1) }

            val feelField by feelFieldState.collectAsState()
            val (feel, feelEror) = feelField

            DropDownMenuTracke(
                modifier = Modifier,
                options = persaanOptions,
                selectedOption = feel,
                onOptionSelected = onChangeFeel,
                label = stringResource(R.string.feel),
                isError = feelEror != null
            )

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
                onClick = {
                    onClickSubmit()
                    onNavigateToTracker()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(
                    "Submit",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
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
