package com.ch2ps215.mentorheal.presentation.twos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FilterCenterFocus
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.model.Detection
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import com.ch2ps215.mentorheal.presentation.twos.component.ArticlesList
import com.ch2ps215.mentorheal.presentation.twos.component.FaceDetectionsContent
import com.ch2ps215.mentorheal.presentation.twos.component.FormDetectionsContent
import com.ch2ps215.mentorheal.presentation.twos.component.SwipeableContainer
import com.ch2ps215.mentorheal.presentation.twos.component.TabRow2Twos
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private val DefaultLazyColumnContentPadding = PaddingValues(16.dp)

@Composable
fun TwosScreen(
    navController: NavHostController,
    viewModel: TwosViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    TwosScreen(
        snackbarHostState = snackbarHostState,
        loadingState = viewModel.loading,
        detectionState = viewModel.detections,
        articlesReduceState = viewModel.articlesReduce,
        articlesReuseState = viewModel.articleReuse,
        onClickFeatureForm = {
            navController.navigate(Route.Form.invoke())
        },
        onClickFeatureCamera = {
            navController.navigate(Route.Detection.invoke())
        },
        onNavigateToDetailArticle = { detection ->

        }
    )
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun TwosScreen(
    snackbarHostState: SnackbarHostState,
    loadingState: StateFlow<Boolean>,
    detectionState: Flow<PagingData<Detection>>,
    articlesReduceState: StateFlow<List<Article>>,
    articlesReuseState: StateFlow<List<Article>>,
    onClickFeatureForm: () -> Unit,
    onClickFeatureCamera: () -> Unit,
    onNavigateToDetailArticle: (Detection) -> Unit
) {
    val pagerState = rememberPagerState {
        ArticlesList.size
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            TopAppBar(title = { Text(text = "Detection") })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            SwipeableContainer(modifier = Modifier.fillMaxSize()) {

                IconButton(
                    onClick = onClickFeatureForm,
                    modifier = Modifier
                        .layoutId("form")
                ) {
                    Icon(
                        imageVector = Icons.Filled.EditNote,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null
                    )
                }

                IconButton(
                    onClick = onClickFeatureCamera,
                    modifier = Modifier
                        .layoutId("camera")
                ) {
                    Icon(
                        imageVector = Icons.Filled.FilterCenterFocus,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null
                    )
                }

                val scope = rememberCoroutineScope()

                TabRow2Twos(
                    modifier = Modifier.layoutId("tab2twos"),
                    selectedTabIndex = pagerState.currentPage,
                    onClickTab = { index ->
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.layoutId("pager2twos"),
                ) { page ->
                    if (page == 0) {
                        FormDetectionsContent(
                            padding = DefaultLazyColumnContentPadding,
                            navigateToDetectionScreen = onNavigateToDetailArticle
                        )
                    }

                    if (page == 1) {
                        FaceDetectionsContent(
                            padding = DefaultLazyColumnContentPadding,
                            navigateToDetectionScreen = onNavigateToDetailArticle
                        )
                    }
                }
            }
        }
    }
}
