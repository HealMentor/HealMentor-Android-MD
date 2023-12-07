package com.ch2ps215.mentorheal.presentation.onboarding.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ch2ps215.mentorheal.R

val OnboardingContent = listOf(
    Triple(
        R.drawable.ic_onboarding1,
        R.string.title_onboarding1,
        R.string.subtitle_onboarding1,
    ),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Pages(
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { OnboardingContent.size }
    )

) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState
    ) { page ->
        val (illustration, title, subtitle) = OnboardingContent[page]
        Page(
            painter = painterResource(illustration),
            title = stringResource(title),
            subTitle = stringResource(subtitle)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun PagesPreview() {
    MaterialTheme {
        Pages(pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { OnboardingContent.size }
        ))
    }
}
