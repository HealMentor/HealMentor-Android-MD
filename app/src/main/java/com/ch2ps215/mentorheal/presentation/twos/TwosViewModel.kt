package com.ch2ps215.mentorheal.presentation.twos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.usecase.GetArticlesUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetDetectionUseCase
import com.ch2ps215.mentorheal.domain.usecase.UpdateDetectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TwosViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    getDetectionUseCase: GetDetectionUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val updateDetectionUseCase: UpdateDetectionUseCase,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    val detections = Pager(
        pagingSourceFactory = { DetectionPagingSource(getDetectionUseCase) },
        config = PagingConfig(pageSize = 10)
    ).flow.onStart {
        _loading.value = true
    }.onEach {
        _loading.value = false
    }.catch { e ->
        Timber.e(e)
        _loading.value = false
        _snackbar.emit("Failed to load detections")
    }.flowOn(dispatcher).cachedIn(viewModelScope)

    private val _articlesReduce = MutableStateFlow(emptyList<Article>())
    val articlesReduce = _articlesReduce.asStateFlow()

    private val _articleReuse = MutableStateFlow(emptyList<Article>())
    val articleReuse = _articleReuse.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

}
