package com.ch2ps215.mentorheal.presentation.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.presentation.home.component.Articles
import com.ch2ps215.mentorheal.presentation.home.component.SearchBar
import com.ch2ps215.mentorheal.presentation.home.component.TopHeader
import com.ch2ps215.mentorheal.presentation.listarticle.ListArticleType
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    HomeScreen(
        snackbarHostState = snackbarHostState,
        onChangeQuerySearch = viewModel::changeQuerySearch,
        usernameState = viewModel.username,
        querySearchState = viewModel.querySearch,
        favoriteArticleState = viewModel.favoriteArticles,
        latestArticleState = viewModel.articles,
        onClickShowMoreChart = {

        },
        onClickShowMoreArticle = { type ->
            navController.navigate(Route.ListArticle(type))
        }
    ) { id ->
        navController.navigate(Route.DetailArticle(id))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    snackbarHostState: SnackbarHostState,
    onChangeQuerySearch: (String) -> Unit,
    usernameState: StateFlow<String?>,
    querySearchState: StateFlow<String>,
    favoriteArticleState: Flow<PagingData<Article>>,
    latestArticleState: Flow<PagingData<Article>>,
    onClickShowMoreChart: () -> Unit,
    onClickShowMoreArticle: (ListArticleType) -> Unit,
    onNavigateToDetailArticle: (String) -> Unit,
) {
    var isSearchBarOpen by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            Crossfade(
                targetState = isSearchBarOpen,
                animationSpec = tween(durationMillis = 500),
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(16.dp),
                label = ""
            ) {
                if (it) {
                    val querySearch by querySearchState.collectAsState()
                    SearchBar(
                        searchText = querySearch,
                        onQueryChange = onChangeQuerySearch,
                        onSearch = { query ->

                        },
                        onBack = {
                            isSearchBarOpen = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                } else {
                    TopAppBar(
                        title = {
                            val username by usernameState.collectAsState()
                            TopHeader(
                                name = username ?: stringResource(R.string.healer),
                                photo = null,
                                onClickProfileImage = { /*TODO*/ }
                            )
                        },
                        actions = {
                            IconButton(onClick = { isSearchBarOpen = true }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = stringResource(R.string.cd_search)
                                )
                            }
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = stringResource(R.string.cd_notification)
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {

            val favoriteArticle = favoriteArticleState.collectAsLazyPagingItems()
            val latestArticle = latestArticleState.collectAsLazyPagingItems()


            Articles(
                label = stringResource(R.string.favorite_article),
                articles = favoriteArticle,
                onClickShowMore = { onClickShowMoreArticle(ListArticleType.Favorite) },
                onClickArticle = onNavigateToDetailArticle
            )

            Articles(
                label = stringResource(R.string.latest_articles),
                articles = latestArticle,
                onClickShowMore = { onClickShowMoreArticle(ListArticleType.Latest) },
                onClickArticle = onNavigateToDetailArticle
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MentorhealTheme {
        HomeScreen(
            snackbarHostState = remember { SnackbarHostState() },
            onChangeQuerySearch = { },
            usernameState = MutableStateFlow("Fulan"),
            querySearchState = MutableStateFlow(""),
            favoriteArticleState = MutableStateFlow(PagingData.empty()),
            latestArticleState = MutableStateFlow(PagingData.empty()),
            onClickShowMoreChart = { },
            onClickShowMoreArticle = { }
        ) { }
    }
}
