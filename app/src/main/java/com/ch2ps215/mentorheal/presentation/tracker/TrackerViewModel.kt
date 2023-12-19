package com.ch2ps215.mentorheal.presentation.tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ch2ps215.mentorheal.domain.model.Article
import com.ch2ps215.mentorheal.domain.model.Tracker
import com.ch2ps215.mentorheal.domain.usecase.GetTrackerUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetUserUseCase
import com.ch2ps215.mentorheal.domain.usecase.SaveTrackerUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateFormUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateTitleUseCase
import com.ch2ps215.mentorheal.presentation.tracker.component.TrackerItem
import com.ch2ps215.mentorheal.presentation.tracker.component.dummyTrackerItems
import com.ch2ps215.mentorheal.presentation.twos.DetectionPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    getTrackerUseCase: GetTrackerUseCase,
    private val saveTrackerUseCase: SaveTrackerUseCase,
    private val dispatcher: CoroutineDispatcher,
    private val validateFormUseCase: ValidateFormUseCase
) : ViewModel() {

    val trackers = Pager(
        pagingSourceFactory = { TrackerPagingSource(getTrackerUseCase) },
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


    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    private val _loading = MutableStateFlow(false)
    val loading : StateFlow<Boolean> = _loading.asStateFlow()

    private val _trackerItems = MutableStateFlow<List<TrackerItem>>(emptyList())
    val trackerItems: StateFlow<List<TrackerItem>> = _trackerItems

    private val _trackerReduce = MutableStateFlow(emptyList<Tracker>())
    val trackerReduce = _trackerReduce.asStateFlow()

    private val _titleField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val titleField = _titleField.asStateFlow()

    private val _starCount = MutableStateFlow<Pair<String, Int?>>("" to null)
    val starCount = _starCount.asStateFlow()

    private val _description = MutableStateFlow<Pair<String, Int?>>("" to null)
    val description = _description.asStateFlow()

    val fulfilled = combine(_titleField, _starCount, _description) { (title, starCount, description) ->
        title.first.isNotBlank()
                && title.second == null
                && starCount.first.isNotBlank()
                && starCount.second == null
                && description.first.isNotBlank()
                && description.second == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun changeTitle(title: String) {
        val error = validateFormUseCase(title)
        _titleField.value = title to error
    }

    fun changeDescription(description: String) {
        val error = validateFormUseCase(description)
        _description.value = description to error
    }

    fun changeStar(star: String) {
        val error = validateFormUseCase(star)
        _starCount.value = star to error
    }

    fun save() {
        viewModelScope.launch(dispatcher) {
            _loading.value = true
            val title = _titleField.value.first
            val starCount = _starCount.value.first.toFloat()
            val description = _description.value.first
            saveTrackerUseCase.invoke(title, starCount, description).onFailure { e ->
                Timber.e(e)
                _snackbar.emit(e.message ?: "Failed to sign in. Try again later")
            }
            _loading.value = false
        }
    }

    init {
        // Replace this with your actual data fetching logic from the database
        // For now, we'll use the dummy data
        _trackerItems.value = dummyTrackerItems
    }

}
