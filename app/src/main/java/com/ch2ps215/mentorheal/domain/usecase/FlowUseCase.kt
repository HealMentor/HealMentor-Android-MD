package com.ch2ps215.mentorheal.domain.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class FlowUseCase<T>(initialValue: T) {
    private val _result = MutableStateFlow(initialValue)
    val result = _result.asStateFlow()

    suspend fun emit(value: T) {
        _result.emit(value)
    }
}
