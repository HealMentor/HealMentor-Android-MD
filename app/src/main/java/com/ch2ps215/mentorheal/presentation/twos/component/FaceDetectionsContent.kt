package com.ch2ps215.mentorheal.presentation.twos.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.ch2ps215.mentorheal.presentation.twos.TwosViewModel

@Composable
fun FaceDetectionsContent(
    viewModel: TwosViewModel = hiltViewModel(),
    padding: PaddingValues,
    navigateToDetectionScreen: (detection: FormDetection) -> Unit
) {
    val pagingDetections = viewModel.detections.collectAsLazyPagingItems()
    val refresh = pagingDetections.loadState.refresh
    val append = pagingDetections.loadState.append
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.empty_box)
    )

    LazyColumn(
        contentPadding = padding,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        if (pagingDetections.itemCount > 0) {
            items(
                count = pagingDetections.itemCount,
                key = pagingDetections.itemKey { detection -> detection.id!! },
                contentType = pagingDetections.itemContentType { "FormDetection" }
            ) { index: Int ->
                pagingDetections[index]?.let { detection ->
                    DetectionCard(
                        formDetection = detection,
                        onDetectionClick = navigateToDetectionScreen
                    )
                }
            }

        } else {
            item {
                LottieAnimation(
                    modifier = Modifier
                        .padding(32.dp)
                        .size(164.dp)
                        .offset(y = (-64).dp),
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                )
            }
        }

    }

    pagingDetections.loadState.apply {
        when {
            refresh is Loading -> {}
            refresh is Error -> print(refresh)
            append is Loading -> {}
            append is Error -> print(append)
        }
    }
}