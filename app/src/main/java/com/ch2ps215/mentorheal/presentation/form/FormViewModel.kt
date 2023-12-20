package com.ch2ps215.mentorheal.presentation.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps215.mentorheal.domain.usecase.DetectFormUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateFormUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateGenderUseCase
import com.ch2ps215.mentorheal.domain.usecase.ValidateYesNoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val validateFormUseCase: ValidateFormUseCase,
    private val validateGenderUseCase: ValidateGenderUseCase,
    private val validateYesNoUseCase: ValidateYesNoUseCase,
    private val dispatcher: CoroutineDispatcher,
    private val detectFormUseCase: DetectFormUseCase,
) : ViewModel() {

    private val _umurField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val umurField = _umurField.asStateFlow()

    private val _genderField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val genderField = _genderField.asStateFlow()

    private val _bidangField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val bidangField = _bidangField.asStateFlow()

    private val _semesterField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val semesterField = _semesterField.asStateFlow()

    private val _cgpaField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val cgpaField = _cgpaField.asStateFlow()

    private val _pernikahanField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val pernikahanField = _pernikahanField.asStateFlow()

    private val _depresiField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val depresiField = _depresiField.asStateFlow()

    private val _kecemasanField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val kecemasanField = _kecemasanField.asStateFlow()

    private val _panicField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val panicField = _panicField.asStateFlow()

    private val _kebutuhankhususField = MutableStateFlow<Pair<String, Int?>>("" to null)
    val kebutuhankhususField = _kebutuhankhususField.asStateFlow()

    val fulfilled = combine(
        _umurField,
    ) { fields ->
        fields.all { (value, error) ->
            value.isNotBlank() && error == null
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    fun changeUmur(age: String) {
        val error = validateFormUseCase(age)
        _umurField.value = age to error
    }

    fun changeGender(gender: String) {
        val error = validateGenderUseCase(gender)
        _genderField.value = gender to error
    }

    fun changeBidang(major: String) {
        val error = validateFormUseCase(major)
        _bidangField.value = major to error
    }

    fun changeSemester(semester: String) {
        val error = validateFormUseCase(semester)
        _semesterField.value = semester to error
    }

    fun changeCGPA(cgpa: String) {
        val error = validateFormUseCase(cgpa)
        _cgpaField.value = cgpa to error
    }

    fun changePernikahan(marriage: String) {
        val error = validateYesNoUseCase(marriage)
        _pernikahanField.value = marriage to error
    }

    fun changeDepresi(depression: String) {
        val error = validateYesNoUseCase(depression)
        _depresiField.value = depression to error
    }

    fun changeKecemasan(anxiety: String) {
        val error = validateYesNoUseCase(anxiety)
        _kecemasanField.value = anxiety to error
    }

    fun changePanic(panic: String) {
        val error = validateYesNoUseCase(panic)
        _panicField.value = panic to error
    }

    fun changeKebutuhanKhusus(treatment: String) {
        val error = validateYesNoUseCase(treatment)
        _kebutuhankhususField.value = treatment to error
    }

    suspend fun submit(): String? {
        if (!fulfilled.value) return null
        _loading.value = true

        detectFormUseCase.invoke(
            age = _umurField.value.first.toInt(),
            gender = _genderField.value.first,
            major = _bidangField.value.first,
            semester = _semesterField.value.first.toInt(),
            cgpa = _cgpaField.value.first.toInt(),
            marriage = _pernikahanField.value.first,
            depression = _depresiField.value.first,
            anxiety = _kecemasanField.value.first,
            panic = _panicField.value.first,
            treatment = _kebutuhankhususField.value.first,
        ).onSuccess {
            _loading.value = false
            return it
        }.onFailure { e ->
            Timber.e(e)
            _snackbar.emit(e.message ?: "Failed to submit the form. Try again later")
        }

        _loading.value = false
        return null
    }

}

