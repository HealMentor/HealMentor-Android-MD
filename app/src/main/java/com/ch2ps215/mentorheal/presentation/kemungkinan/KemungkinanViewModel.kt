package com.ch2ps215.mentorheal.presentation.kemungkinan

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class KemungkinanViewModel @Inject constructor() : ViewModel() {

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()
}