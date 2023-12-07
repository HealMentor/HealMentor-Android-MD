package com.ch2ps215.mentorheal.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps215.mentorheal.domain.usecase.SignInUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateEmailUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val signInUseCase: SignInUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _emailField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val emailField = _emailField.asStateFlow()

    private val _passwordField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val passwordField = _passwordField.asStateFlow()

    val fulfilled = combine(_emailField, _passwordField) { (email, password) ->
        email.first.isNotBlank()
                && email.second == null
                && password.first.isNotBlank()
                && password.second == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    fun changeEmail(email: String) {
        val error = validateEmailUseCase(email)
        _emailField.value = email to error
    }

    fun changePassword(password: String) {
        val error = validatePasswordUseCase(password)
        _passwordField.value = password to error
    }

    fun signIn() {
        viewModelScope.launch(dispatcher) {
            if (!fulfilled.value) return@launch
            _loading.value = true
            val email = _emailField.value.first
            val password = _passwordField.value.first
            signInUseCase.invoke(email, password).onFailure { e ->
                Timber.e(e)
                _snackbar.emit("Failed to sign in")
            }
            _loading.value = false
        }
    }
}

