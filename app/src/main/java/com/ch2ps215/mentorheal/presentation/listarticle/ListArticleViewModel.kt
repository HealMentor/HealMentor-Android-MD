package com.ch2ps215.mentorheal.presentation.listarticle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.usecase.GetArticlesUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetFavoriteArticlesUseCase
import com.ch2ps215.mentorheal.presentation.common.PagerUtils.createFirestorePager
import com.ch2ps215.mentorheal.presentation.navgraph.Route.Companion.LIST_ARTICLE_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListArticleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFavoriteArticlesUseCase: GetFavoriteArticlesUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
) : ViewModel() {

    private val _articles = MutableStateFlow<PagingData<Article>>(PagingData.empty())
    val articles = _articles.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    private val _type = MutableStateFlow(ListArticleType.Favorite)
    val type = _type.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        savedStateHandle.get<String>(LIST_ARTICLE_TYPE)?.let {
            _type.value = ListArticleType.valueOf(it)
        }
    }

    fun loadArticles() {
        viewModelScope.launch {
            when (_type.value) {
                ListArticleType.Favorite -> {
                    _articles.update { latestArticles("favorite").first() }
                }

                ListArticleType.Latest -> {
                    _articles.update { latestArticles("").first() }
                }

                ListArticleType.Happy -> {
                    _articles.update { latestArticles("happy").first() }
                }

                ListArticleType.Depression -> {
                    _articles.update { latestArticles("depression").first() }
                }
            }
        }
    }

    private fun latestArticles(category: String) = createFirestorePager(Article::class.java) {
        if (category.isEmpty()) {
            getArticlesUseCase().getOrThrow()
        } else if (category == "favorite") {
            getFavoriteArticlesUseCase().getOrThrow()
        } else {
            getArticlesUseCase(category).getOrThrow()
        }
    }.flow.onStart {
        _loading.value = true
    }.onEach {
        _loading.value = false
    }.catch { e ->
        Timber.e(e)
        _loading.value = false
        _snackbar.emit("Failed to load articles")
    }

    fun onChangeCategory(filter: ListArticleType) {
        _type.value = filter
        loadArticles()
    }
}
