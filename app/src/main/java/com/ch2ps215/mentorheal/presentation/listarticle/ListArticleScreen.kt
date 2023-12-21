package com.ch2ps215.mentorheal.presentation.listarticle

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.presentation.home.component.ArticleContent
import com.ch2ps215.mentorheal.presentation.listarticle.component.FilterArticleDialog
import com.ch2ps215.mentorheal.presentation.navgraph.Route

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListArticleScreen(
    navController: NavHostController,
    viewModel: ListArticleViewModel = hiltViewModel()
) {

    var isFilterOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadArticles()
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            val type by viewModel.type.collectAsState()

            TopAppBar(
                title = {
                    Text(
                        text = when (type) {
                            ListArticleType.Favorite -> stringResource(R.string.favorite_article)
                            ListArticleType.Latest -> stringResource(R.string.latest_articles)
                            ListArticleType.Happy -> stringResource(R.string.article)
                            ListArticleType.Depression -> stringResource(R.string.article)
                        }
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            Icons.Rounded.ArrowBackIos,
                            stringResource(R.string.cd_back)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            isFilterOpen = true
                        }
                    ) {
                        Icon(
                            Icons.Filled.FilterList,
                            stringResource(R.string.cd_filter_article)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        val articles = viewModel.articles.collectAsLazyPagingItems()
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(
                    count = articles.itemCount,
                    key = articles.itemKey { article -> article.id!! },
                    contentType = articles.itemContentType { "Article" }
                ) { index: Int ->
                    articles[index]?.let { article ->
                        ArticleContent(
                            id = article.id!!,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .animateItemPlacement(),
                            title = article.title!!,
                            onClick = {
                                navController.navigate(Route.DetailArticle(article.id))
                            },
                            photo = article.photo.getOrNull(0)!!
                        )
                    }
                }
            }

            val isLoading by viewModel.loading.collectAsState()
            val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.empty_box)
            )

            if (articles.itemCount <= 0 && !isLoading) {
                LottieAnimation(
                    modifier = Modifier
                        .padding(32.dp)
                        .size(164.dp)
                        .align(Alignment.Center)
                        .offset(y = (-24).dp),
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                )
            }

            if (isFilterOpen) {
                FilterArticleDialog(
                    onDismiss = {
                        isFilterOpen = false
                    },
                    onFilter = {
                        viewModel.onChangeCategory(it)
                        isFilterOpen = false
                    }
                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
