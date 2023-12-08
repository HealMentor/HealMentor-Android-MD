package com.ch2ps215.mentorheal.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.usecase.GetArticlesUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val username = getUserUseCase()
        .map { it?.name }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _querySearch = MutableStateFlow("")
    val querySearch = _querySearch.asStateFlow()

    private val _latestArticles = MutableStateFlow(emptyList<Article>())
    val latestArticles = _latestArticles.asStateFlow()

    private val _articlesReduce = MutableStateFlow(emptyList<Article>())
    val articlesReduce = _articlesReduce.asStateFlow()

    private val _articleReuse = MutableStateFlow(emptyList<Article>())
    val articleReuse = _articleReuse.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    init {
        loadLatestArticles()
        loadArticlesReduce()
    }

    private fun loadLatestArticles() {
        viewModelScope.launch(dispatcher) {
            getArticlesUseCase()
                .onSuccess { articles ->
                    _latestArticles.value = articles
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load articles")
                }
        }
    }

    private fun loadArticlesReduce() {
        viewModelScope.launch(dispatcher) {
            getArticlesUseCase(category = "Reduce")
                .onSuccess { articles ->
                    _articlesReduce.value = articles
                }
                .onFailure { e ->
                    Timber.e(e)
                    _snackbar.emit("Failed to load articles about reduce")
                }
        }
    }

    fun changeQuerySearch(querySearch: String) {
        _querySearch.value = querySearch
    }
}
