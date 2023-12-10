package com.ch2ps215.mentorheal.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps215.mentorheal.domain.usecase.SignUpUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateEmailUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateNameUseCase
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
class SignUpViewModel @Inject constructor(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _nameField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val nameField = _nameField.asStateFlow()

    private val _emailField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val emailField = _emailField.asStateFlow()

    private val _passwordField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val passwordField = _passwordField.asStateFlow()

    private val _confirmPasswordField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val confirmPasswordField = _confirmPasswordField.asStateFlow()

    val fulfilled = combine(
        _nameField,
        _emailField,
        _passwordField,
        _confirmPasswordField
    ) { (name, email, password, confirmPassword) ->
        name.first.isNotBlank()
                && name.second == null
                && email.first.isNotBlank()
                && email.second == null
                && password.first.isNotBlank()
                && password.second == null
                && confirmPassword.first.isNotBlank()
                && confirmPassword.second == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    fun changeName(name: String) {
        val error = validateNameUseCase(name)
        _nameField.value = name to error
    }

    fun changeEmail(email: String) {
        val error = validateEmailUseCase(email)
        _emailField.value = email to error
    }

    fun changePassword(password: String) {
        val error = validatePasswordUseCase(password)
        val error2 = validatePasswordUseCase(password, _confirmPasswordField.value.first)
        _passwordField.value = password to error
        _confirmPasswordField.value = _confirmPasswordField.value.first to error2
    }

    fun changeConfirmPassword(password: String) {
        val error2 = validatePasswordUseCase(_passwordField.value.first, password)
        _confirmPasswordField.value = password to error2
    }

    fun signUp() {
        viewModelScope.launch(dispatcher) {
            if (!fulfilled.value) return@launch
            _loading.value = true
            val name = _nameField.value.first
            val email = _emailField.value.first
            val password = _passwordField.value.first
            signUpUseCase.invoke(name, email, password).onFailure { e ->
                Timber.e(e)
                _snackbar.emit(e.message ?: "Failed to sign in. Try again later")
            }
            _loading.value = false
        }
    }
}

