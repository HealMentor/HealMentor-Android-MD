package com.ch2ps215.mentorheal.presentation.kemungkinan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.ch2ps215.mentorheal.presentation.common.component.TopAppBar
import com.ch2ps215.mentorheal.presentation.kemungkinan.component.CardWithFavorite
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun KemungkinanScreen(
    navController: NavHostController,
    viewModel: KemungkinanViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackBarHostState::showSnackbar)
    }

    KemungkinanScreen(
        snackBarHostState = snackBarHostState,
        latestFormState = viewModel.latestForm,
        onNavigateBack = navController::popBackStack,
        onClick = { navController.navigate(Route.AtasiKecemasan()) }
    )
}

@Composable
fun KemungkinanScreen(
    snackBarHostState: SnackbarHostState,
    latestFormState: StateFlow<List<FormDetection>>,
    onNavigateBack: () -> Unit = { },
    onClick: () -> Unit = { }
) {

    val latestFrom by latestFormState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = "Kemungkinan masalah",
                onNavigationBack = onNavigateBack
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(latestFrom.size) { form ->
                val label = if (latestFrom[form].depression.equals(
                        "Ya",
                        ignoreCase = true
                    )
                ) "Depressi" else "Happy"
                CardWithFavorite(
                    title = label,
                    score = latestFrom[form].scores?.toFloat() ?: 0f,
                    onClick = onClick,
                )
            }
        }
    }
}

@Composable
fun FavoriteButton(
    onFavoriteToggle: () -> Unit,
    isFavorited: Boolean
) {
    var isFavorite by remember { mutableStateOf(isFavorited) }

    Icon(
        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
        contentDescription = null,
        tint = if (isFavorite) Color.Red else Color.Black,
        modifier = Modifier
            .size(24.dp)
            .clickable {
                isFavorite = !isFavorite
                onFavoriteToggle()
            }
    )
}

@Preview
@Composable
fun KemungkinanScreenPreview() {
    MentorhealTheme {
        KemungkinanScreen(
            snackBarHostState = remember { SnackbarHostState() },
            latestFormState = MutableStateFlow(emptyList()),
        )
    }
}
