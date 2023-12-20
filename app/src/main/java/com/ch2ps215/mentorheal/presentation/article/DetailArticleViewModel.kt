package com.ch2ps215.mentorheal.presentation.article

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.usecase.GetArticleUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetFavoriteArticlesUseCase
import com.ch2ps215.mentorheal.domain.usecase.LikingArticleUseCase
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailArticleViewModel @Inject constructor(
    private val getFavoriteArticlesUseCase: GetFavoriteArticlesUseCase,
    private val getArticleUseCase: GetArticleUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val toggleLikeArticleUseCase: LikingArticleUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _article = MutableStateFlow<Article?>(null)
    val article = _article.asStateFlow()

    private val _isLiked = MutableStateFlow<Boolean?>(null)
    val isLiked = _isLiked.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    private var job: Job? = null

    init {
        loadArticle()
    }

    fun toggleLike(articleId: String) {
        job?.cancel()
        job = viewModelScope.launch(dispatcher) {
            delay(250)
            toggleLikeArticleUseCase.invoke(isLiked.first()!!, articleId)
            _isLiked.value = _isLiked.value?.not()
        }
    }

    private fun loadArticle() {
        viewModelScope.launch(dispatcher) {
            val id = savedStateHandle.get<String>(Route.KEY_ARTICLE_ID) ?: return@launch
            getArticleUseCase(id)
                .onSuccess { article ->
                    _article.value = article
                }
                .onFailure { e ->
                    _snackbar.emit(e.message ?: "Failed to load article")
                }

            article.value?.id?.let {
                getFavoriteArticlesUseCase.invoke(it).getOrDefault(false)
            }?.let {
                _isLiked.value = it
            }
        }
    }

}
