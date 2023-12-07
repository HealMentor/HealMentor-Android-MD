package com.ch2ps215.mentorheal.presentation.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.presentation.home.component.Articles
import com.ch2ps215.mentorheal.presentation.home.component.Header
import com.ch2ps215.mentorheal.presentation.home.component.SearchBar
import com.ch2ps215.mentorheal.presentation.listarticle.ListArticleType
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
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
        querySearchState = viewModel.querySearch,
        usernameState = viewModel.username,
        latestArticlesState = viewModel.latestArticles,
        articlesReduce = viewModel.articlesReduce,
        articlesReuse = viewModel.articleReuse,
        onClickShowMoreChart = {

        },
        onClickShowMoreArticle = { type ->

        },
        onNavigateToDetailArticle = { id ->

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    snackbarHostState: SnackbarHostState,
    onChangeQuerySearch: (String) -> Unit,
    usernameState: StateFlow<String?>,
    querySearchState: StateFlow<String>,
    latestArticlesState: StateFlow<List<Article>>,
    articlesReduce: StateFlow<List<Article>>,
    articlesReuse: StateFlow<List<Article>>,
    onClickShowMoreChart: () -> Unit,
    onClickShowMoreArticle: (ListArticleType) -> Unit,
    onNavigateToDetailArticle: (Int) -> Unit,
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
                modifier = Modifier.statusBarsPadding(),
                label = ""
            ) {
                if (it) {
                    val querySearch by querySearchState.collectAsState()
                    SearchBar(
                        modifier = Modifier
                            .statusBarsPadding(),
                        searchText = querySearch ?: "",
                        onQueryChange = onChangeQuerySearch,
                        onSearch = { query ->

                        },
                        onBack = {
                            isSearchBarOpen = false
                        }
                    )
                } else {
                    TopAppBar(
                        title = {
                            val username by usernameState.collectAsState()
                            Header(
                                modifier = Modifier
                                    .statusBarsPadding()
                                    .padding(horizontal = 16.dp, vertical = 32.dp),
                                name = username,
                                photo = null,
                                onClickShowMore = onClickShowMoreChart,
                                onClickProfileImage = { }
                            )
                        },
                        actions = {
                            IconButton(onClick = { isSearchBarOpen = true }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = stringResource(R.string.cd_search)
                                )
                            }
                        }
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

            val latestArticles by latestArticlesState.collectAsState()
            val reuse by articlesReuse.collectAsState()

            Articles(
                label = stringResource(R.string.ongoing_activity),
                articles = latestArticles,
                onClickShowMore = { onClickShowMoreArticle(ListArticleType.Latest) },
                onClickArticle = onNavigateToDetailArticle
            )
            Articles(
                label = stringResource(R.string.activity_recommendation),
                articles = reuse,
                onClickShowMore = { onClickShowMoreArticle(ListArticleType.Favorite) },
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
            querySearchState = MutableStateFlow(""),
            usernameState = MutableStateFlow("Tubagus"),
            latestArticlesState = MutableStateFlow(emptyList()),
            articlesReuse = MutableStateFlow(emptyList()),
            articlesReduce = MutableStateFlow(emptyList()),
            onNavigateToDetailArticle = { },
            onClickShowMoreChart = { },
            onClickShowMoreArticle = { }
        )
    }
}
