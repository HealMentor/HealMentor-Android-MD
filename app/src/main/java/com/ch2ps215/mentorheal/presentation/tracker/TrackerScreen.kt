package com.ch2ps215.mentorheal.presentation.tracker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.domain.model.Tracker
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import com.ch2ps215.mentorheal.presentation.tracker.component.SearchWithAddButton
import com.ch2ps215.mentorheal.presentation.tracker.component.TrackerContent
import com.ch2ps215.mentorheal.presentation.tracker.component.TrackerItem
import com.ch2ps215.mentorheal.presentation.twos.component.TrackerList
import kotlinx.coroutines.flow.collectLatest

private val DefaultLazyColumnContentPadding = PaddingValues(16.dp)

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

    TrackerScreenView(
        navController = navController,
        snackBarHostState = snackBarHostState,
        trackerItems = trackerItems,
        onNavigateToDetail = { tracker ->

        },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TrackerScreenView(
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    trackerItems: List<TrackerItem>,
    onNavigateToDetail: (Tracker) -> Unit
) {

    var searchQuery by remember { mutableStateOf("") }

    // Menyaring item berdasarkan judul (title)
    val filteredItems = trackerItems.filter { item ->
        item.title.contains(searchQuery, ignoreCase = true)
    }

    val pagerState = rememberPagerState {
        TrackerList
    }

    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.tracker),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.title),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.subtitle),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            SearchWithAddButton(
                onSearchClick = { query ->
                    searchQuery = query
                },
                onAddClick = {
                    navController.navigate(Route.AddTracker())
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.title_list),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.layoutId("pager2twos"),
            ) { page ->
                TrackerContent(
                    padding = DefaultLazyColumnContentPadding,
                    navigateToDetectionScreen = onNavigateToDetail
                )
            }
            // Menampilkan item yang sudah disaring

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
    val navController = rememberNavController()

    MentorhealTheme {
        TrackerScreen(
            navController = navController
        )
    }
}