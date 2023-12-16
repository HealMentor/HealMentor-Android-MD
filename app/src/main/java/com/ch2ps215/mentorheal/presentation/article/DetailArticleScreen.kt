package com.ch2ps215.mentorheal.presentation.article

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.presentation.article.component.Photos
import com.ch2ps215.mentorheal.presentation.common.component.shimmer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailArticleScreen(
    navController: NavHostController,
    viewModel: DetailArticleViewModel = hiltViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    DetailArticleScreen(
        snackbarHostState = snackbarHostState,
        articleState = viewModel.article,
        likedState = viewModel.liked,
        onClickButtonLike = viewModel::toggleLike,
        onNavigationBack = navController::popBackStack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailArticleScreen(
    snackbarHostState: SnackbarHostState,
    articleState: StateFlow<Article?>,
    likedState: StateFlow<Boolean>,
    onClickButtonLike: (Int) -> Unit,
    onNavigationBack: () -> Unit,
) {
    val article by articleState.collectAsState( )
    val isLiked by likedState.collectAsState()

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = article?.title ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigationBack) {
                        Icon(
                            Icons.Rounded.ArrowBackIos,
                            stringResource(R.string.cd_back)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            TODO()
                        },
                        enabled = article != null
                    ) {
                        when (isLiked) {
                            true -> Icon(
                                imageVector = Icons.Outlined.Favorite,
                                contentDescription = stringResource(R.string.cd_favorite_article)
                            )

                            else -> Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = stringResource(R.string.cd_favorite_article)
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            Photos(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .shimmer(article == null),
                photos =  article?.photo ?: emptyList()
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    .shimmer(article == null),
                text = article?.title ?: "Title Placeholder",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .shimmer(article == null),
                text = article?.author ?: "Author Placeholder",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .shimmer(article == null),
                text = "Date Placeholder",
                style = MaterialTheme.typography.bodySmall
            )
            article?.body?.let { body ->
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(8.dp)
                        .shimmer(article == null),
                    text = body,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            } ?: run {
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                        .weight(1F)
                        .shimmer(article == null)
                )
            }
        }
    }
}

@Preview
@Composable
fun DetailArticleScreenPreview() {
    DetailArticleScreen(
        snackbarHostState = remember { SnackbarHostState() },
        articleState = MutableStateFlow(
            Article(
                id = "1",
                title = "Title Placeholder",
                author = "Author Placeholder",
                body = "Body Placeholder",
                photo = listOf("https://picsum.photos/200/300"),
            )
        ),
        likedState = MutableStateFlow(false),
        onClickButtonLike = {},
        onNavigationBack = {}
    )
}
