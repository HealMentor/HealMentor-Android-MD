package com.ch2ps215.mentorheal.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps215.mentorheal.domain.usecase.DarkThemeUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetUserUseCase
import com.ch2ps215.mentorheal.domain.usecase.OnboardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dispatcher: CoroutineDispatcher,
    onboardingUseCase: OnboardingUseCase,
    getUserUseCase: GetUserUseCase,
    darkThemeUseCase: DarkThemeUseCase
) : ViewModel() {
    val onboarding = onboardingUseCase.invoke()
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.Lazily, false)
    val loggedIn = getUserUseCase()
        .map { it != null }
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
    val darkTheme = darkThemeUseCase()
        .flowOn(dispatcher)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}
