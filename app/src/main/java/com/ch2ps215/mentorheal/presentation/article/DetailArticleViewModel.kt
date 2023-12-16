package com.ch2ps215.mentorheal.presentation.article

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.usecase.GetArticleUseCase
import com.ch2ps215.mentorheal.domain.usecase.LikingArticleUseCase
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailArticleViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val toggleLikeArticleUseCase: LikingArticleUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _article = MutableStateFlow<Article?>(null)
    val article = _article.asStateFlow()

    private val _liked = MutableStateFlow(false)
    val liked = _liked.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    private var job: Job? = null

    init {
        loadArticle()
    }

    fun toggleLike(articleId: Int) {
        job?.cancel()
        TODO()
        /*_article.value?.let { prevValue ->

            job = viewModelScope.launch(dispatcher) {
                _article.value = _article.value?.copy(liked = !prevValue.liked)
                delay(250)
               toggleLikeArticleUseCase.invoke(articleId, !prevValue.liked)
                   .onSuccess { article->
                       _article.value = article
                   }
                   .onFailure {
                       _article.value = prevValue
                   }
            }
        }*/
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
        }
    }

}
