package com.ch2ps215.mentorheal.presentation.listarticle

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.usecase.GetArticlesUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetFavoriteArticlesUseCase
import com.ch2ps215.mentorheal.presentation.common.PagerUtils.createFirestorePager
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListArticleViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    dispatcher: CoroutineDispatcher,
    private val getFavoriteArticlesUseCase: GetFavoriteArticlesUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
) : ViewModel() {

    private val _articles = MutableStateFlow<Flow<PagingData<Article>>>(emptyFlow())
    val articles
        get() = _articles.value

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    private val _type = MutableStateFlow(ListArticleType.Favorite)
    val type = _type.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun loadArticles() {
        savedStateHandle.get<String>(Route.LIST_ARTICLE_TYPE)?.let { type ->
            _type.value = ListArticleType.valueOf(type)

            when (ListArticleType.valueOf(type)) {
                ListArticleType.Favorite -> {
                    _articles.value = articlesFavorite
                }

                ListArticleType.Latest -> {
                    _articles.value = latestArticles
                }
            }
        }
    }

    private val articlesFavorite = createFirestorePager(Article::class.java) {
        getArticlesUseCase().getOrThrow()
    }.flow.onStart {
        _loading.value = true
    }.onEach {
        _loading.value = false
    }.catch { e ->
        Timber.e(e)
        _loading.value = false
        _snackbar.emit("Failed to load detections")
    }.flowOn(dispatcher).cachedIn(viewModelScope)

    private val latestArticles = createFirestorePager(Article::class.java) {
        getArticlesUseCase().getOrThrow()
    }.flow.onStart {
        _loading.value = true
    }.onEach {
        _loading.value = false
    }.catch { e ->
        Timber.e(e)
        _loading.value = false
        _snackbar.emit("Failed to load detections")
    }.flowOn(dispatcher).cachedIn(viewModelScope)

    override fun onCleared() {
        super.onCleared()
        Timber.i("Deleted ListArticleViewModel")
    }
}
