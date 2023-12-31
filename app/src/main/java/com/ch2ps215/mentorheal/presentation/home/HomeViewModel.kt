package com.ch2ps215.mentorheal.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.usecase.GetArticlesUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetFavoriteArticlesUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetUserUseCase
import com.ch2ps215.mentorheal.presentation.common.PagerUtils.createFirestorePager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
    private val getFavoriteArticlesUseCase: GetFavoriteArticlesUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val username = getUserUseCase()
        .map { it?.name }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _querySearch = MutableStateFlow("")
    val querySearch = _querySearch.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    var favoriteArticles = createFirestorePager(Article::class.java) {
        getFavoriteArticlesUseCase().getOrThrow()
    }.flow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())

    val articles = createFirestorePager(Article::class.java) {
        getArticlesUseCase().getOrThrow()
    }.flow.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())

    fun changeQuerySearch(querySearch: String) {
        _querySearch.value = querySearch
    }
}
