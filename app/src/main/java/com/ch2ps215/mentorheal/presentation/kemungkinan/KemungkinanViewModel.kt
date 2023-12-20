package com.ch2ps215.mentorheal.presentation.kemungkinan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps215.mentorheal.domain.model.FormDetection
import com.ch2ps215.mentorheal.domain.usecase.GetFormDetectionUseCase
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KemungkinanViewModel @Inject constructor(
    private val getFormDetectionUseCase: GetFormDetectionUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _detectionLoading = MutableStateFlow(false)
    val detectionLoading = _detectionLoading.asStateFlow()

    private val _latestForm = MutableStateFlow(emptyList<FormDetection>())
    val latestForm = _latestForm.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    init {
        getFormDetectionResult()
    }

    private fun getFormDetectionResult() {
        viewModelScope.launch {
            val idForm = savedStateHandle.get<String>(Route.KEY_FORM_ID) ?: return@launch
            getFormDetectionUseCase(idForm)
                .onSuccess { query ->
                    query.get().addOnSuccessListener {
                        _latestForm.value = it.toObjects(FormDetection::class.java)
                    }
                }.onFailure {
                    _snackbar.emit(it.localizedMessage ?: "Unknown error")
                }
        }
    }
}