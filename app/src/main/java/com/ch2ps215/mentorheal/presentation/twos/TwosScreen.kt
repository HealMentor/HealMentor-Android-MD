package com.ch2ps215.mentorheal.presentation.twos

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ch2ps215.mentorheal.domain.model.ExpressionDetection
import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.ch2ps215.mentorheal.presentation.camera.CameraActivity
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import com.ch2ps215.mentorheal.presentation.twos.component.ArticlesList
import com.ch2ps215.mentorheal.presentation.twos.component.FaceDetectionsContent
import com.ch2ps215.mentorheal.presentation.twos.component.FormDetectionsContent
import com.ch2ps215.mentorheal.presentation.twos.component.SwipeableContainer
import com.ch2ps215.mentorheal.presentation.twos.component.TabRow2Twos
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

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
        onDetect = viewModel::detectExpression,
        onClickFeatureForm = {
            navController.navigate(Route.Form.invoke())
        },
        onNavigateToDetailDetections = { detection ->

        }
    ) { detection ->

    }
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun TwosScreen(
    snackbarHostState: SnackbarHostState,
    onDetect: (File) -> Unit,
    onClickFeatureForm: () -> Unit,
    onNavigateToDetailDetections: (FormDetection) -> Unit,
    onNavigateToDetailExpressionArticle: (ExpressionDetection) -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val cameraLauncher = rememberCameraLauncher(
        onSuccess = onDetect,
        onFailed = {
            scope.launch { snackbarHostState.showSnackbar("Failed to take picture") }
        }
    )

    val pagerState = rememberPagerState {
        ArticlesList.size
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            TopAppBar(title = { Text(text = "FormDetection") })
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
                    onClick = {
                        val intent = Intent(context, CameraActivity::class.java)
                        cameraLauncher.launch(intent)
                    },
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
                            navigateToDetectionScreen = onNavigateToDetailDetections
                        )
                    }

                    if (page == 1) {
                        FaceDetectionsContent(
                            padding = DefaultLazyColumnContentPadding,
                            navigateToDetectionExpressionScreen = onNavigateToDetailExpressionArticle
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun rememberCameraLauncher(
    onFailed: () -> Unit = { },
    onSuccess: (File) -> Unit = { }
): ManagedActivityResultLauncher<Intent, ActivityResult> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode != CameraActivity.RESULT_SUCCESS) {
                onFailed()
                return@rememberLauncherForActivityResult
            }

            val data = result.data
            val imageFile = data?.getSerializableExtra(CameraActivity.KEY_IMAGE_RESULT) as File
            onSuccess(imageFile)
        }
    )
}
