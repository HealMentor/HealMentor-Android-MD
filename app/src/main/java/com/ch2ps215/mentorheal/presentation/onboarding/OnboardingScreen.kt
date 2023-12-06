package com.ch2ps215.mentorheal.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import com.ch2ps215.mentorheal.presentation.onboarding.component.OnboardingContent
import com.ch2ps215.mentorheal.presentation.onboarding.component.Pages
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    OnboardingScreen(
        onboarding = viewModel::onboarding,
        onNavigateToSignInScreen = {
            viewModel.onboarding(true)
            navController.navigate(Route.SignIn())
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onboarding: (Boolean) -> Unit,
    onNavigateToSignInScreen: () -> Unit
) {
    Column(
        modifier = Modifier
//            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .heightIn(900.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.primary)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { OnboardingContent.size }

        )

        Pages(
            modifier = Modifier.weight(1F),
            pagerState = pagerState
        )

        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = OnboardingContent.size,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )

        val isLastPage by remember { derivedStateOf { pagerState.currentPage == OnboardingContent.size - 1 } }
        val scope = rememberCoroutineScope()

        Button(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onClick = {
                scope.launch {
                    if (isLastPage) {
                        onboarding(true)
                        return@launch
                    }
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        ) {
            Text(text = stringResource(R.string.get_started), color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    MentorhealTheme {
        OnboardingScreen(
            onNavigateToSignInScreen = {},
            onboarding = { }
        )
    }
}
