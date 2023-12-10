package com.ch2ps215.mentorheal.presentation.tracker

import androidx.lifecycle.ViewModel
import com.ch2ps215.mentorheal.domain.usecase.GetUserUseCase
import com.ch2ps215.mentorheal.presentation.tracker.component.TrackerItem
import com.ch2ps215.mentorheal.presentation.tracker.component.dummyTrackerItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class TrackerViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    private val _trackerItems = MutableStateFlow<List<TrackerItem>>(emptyList())
    val trackerItems: StateFlow<List<TrackerItem>> = _trackerItems

    init {
        // Replace this with your actual data fetching logic from the database
        // For now, we'll use the dummy data
        _trackerItems.value = dummyTrackerItems
    }

}
