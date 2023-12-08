package com.ch2ps215.mentorheal.presentation.kemungkinan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ch2ps215.mentorheal.presentation.common.component.MyTopBar
import com.ch2ps215.mentorheal.presentation.kemungkinan.component.CardWithFavorite
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun KemungkinanScreen(
    navController: NavHostController,
    viewModel: KemungkinanViewModel
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackBarHostState::showSnackbar)
    }

    KemungkinanScreen(
        snackBarHostState = snackBarHostState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KemungkinanScreen(
    snackBarHostState: SnackbarHostState,
) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            MyTopBar(
                title = "Kemungkinan masalah",
                navController = navController
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            items(3) {
                CardWithFavorite(
                    title = "Card Title $it",
                    description = "This is a sample card description for Card $it.",
                    onFavoriteToggle = {  },
                    isFavorited = false,
                    onClick = {
                        navController.navigate(Route.AtasiKecemasan())
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
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
        )
    }
}
