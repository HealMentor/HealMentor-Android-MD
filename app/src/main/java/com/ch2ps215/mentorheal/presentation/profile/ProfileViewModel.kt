package com.ch2ps215.mentorheal.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps215.mentorheal.domain.usecase.DarkThemeUseCase
import com.ch2ps215.mentorheal.domain.usecase.GetUserUseCase
import com.ch2ps215.mentorheal.domain.usecase.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val dispatcher: CoroutineDispatcher,
    private val signOutUseCase: SignOutUseCase,
    private val darkThemeUseCase: DarkThemeUseCase
) : ViewModel() {
    val user = getUserUseCase()
        .filterNotNull()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    val darkTheme = darkThemeUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase().onFailure { e ->
                Timber.e(e)
                _snackbar.emit("Sign out was failed")
            }
        }
    }

    fun darkTheme(isDarkTheme: Boolean?) {
        viewModelScope.launch(dispatcher) {
            darkThemeUseCase(isDarkTheme)
        }
    }
}
