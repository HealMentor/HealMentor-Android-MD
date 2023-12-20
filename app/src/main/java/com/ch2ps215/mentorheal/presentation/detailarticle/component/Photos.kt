package com.ch2ps215.mentorheal.presentation.detailarticle.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.HorizontalPagerIndicator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Photos(
    modifier: Modifier = Modifier,
    photos: List<String>
) {
    val photosOf4 = remember(photos) { photos.take(4) }

    Box(modifier = modifier) {
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { photosOf4.size }

        )

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) { index ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photosOf4[index])
                    .crossfade(600)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        if (photos.size != 1) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                pageCount = photos.size,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.BottomCenter),
                indicatorShape = RoundedCornerShape(8.dp),
                indicatorWidth = 32.dp,
                indicatorHeight = 6.dp,
                spacing = 12.dp,
                activeColor = MaterialTheme.colorScheme.primary,
                inactiveColor = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Preview
@Composable
fun PhotosPreview() {
    Photos(
        photos = listOf(
            "https://images.unsplash.com/photo-1632836809477-5b9b9b5b9b9b?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0Mnx8fGVufDB8fHx8&ixlib=rb-1.2.1&w=1000&q=80",
            "https://images.unsplash.com/photo-1632836809477-5b9b9b5b9b9b?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0Mnx8fGVufDB8fHx8&ixlib=rb-1.2.1&w=1000&q=80",
            "https://images.unsplash.com/photo-1632836809477-5b9b9b5b9b9b?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0Mnx8fGVufDB8fHx8&ixlib=rb-1.2.1&w=1000&q=80",
            "https://images.unsplash.com/photo-1632836809477-5b9b9b5b9b9b?ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0Mnx8fGVufDB8fHx8&ixlib=rb-1.2.1&w=1000&q=80"
        )
    )
}
