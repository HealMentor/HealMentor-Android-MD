package com.ch2ps215.mentorheal.presentation.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps215.mentorheal.domain.usecase.GetUserUseCase
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val validateFormUseCase: ValidateFormUseCase,
    private val validateGenderUseCase: ValidateGenderUseCase,
    private val validateYesNoUseCase: ValidateYesNoUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val username = getUserUseCase()
        .map { it?.name }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _umurField= MutableStateFlow<Pair<String, Int?>>("" to null)
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
        _genderField,
        _bidangField,
        _semesterField,
        _cgpaField,
        _pernikahanField,
        _depresiField,
        _kecemasanField,
        _panicField,
        _kebutuhankhususField
    ) { fields ->
        fields.all { (value, error) ->
            value.isNotBlank() && error == null
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbar = _snackbar.asSharedFlow()

    fun changeUmur(umur: String) {
        val error = validateFormUseCase(umur)
        _umurField.value = umur to error
    }

    fun changeGender(gender: String) {
        val error = validateGenderUseCase(gender)
        _genderField.value = gender to error
    }

    fun changeBidang(bidang: String) {
        val error = validateFormUseCase(bidang)
        _umurField.value = bidang to error
    }

    fun changeSemester(semester: String) {
        val error = validateFormUseCase(semester)
        _umurField.value = semester to error
    }

    fun changeCGPA(cgpa: String) {
        val error = validateFormUseCase(cgpa)
        _umurField.value = cgpa to error
    }

    fun changePernikahan(pernikahan: String) {
        val error = validateYesNoUseCase(pernikahan)
        _pernikahanField.value = pernikahan to error
    }

    fun changeDepresi(depresi: String) {
        val error = validateYesNoUseCase(depresi)
        _depresiField.value = depresi to error
    }

    fun changeKecemasan(kecemasan: String) {
        val error = validateYesNoUseCase(kecemasan)
        _kecemasanField.value = kecemasan to error
    }

    fun changePanic(panic: String) {
        val error = validateYesNoUseCase(panic)
        _panicField.value = panic to error
    }

    fun changeKebutuhanKhusus(kebutuhankhusus: String) {
        val error = validateYesNoUseCase(kebutuhankhusus)
        _kebutuhankhususField.value = kebutuhankhusus to error
    }

//    fun submit() {
//        viewModelScope.launch(dispatcher) {
//            if (!fulfilled.value) return@launch
//            _loading.value = true
//            val umur = _umurField.value.first
//            val gender = _genderField.value.first
//            val bidang = _bidangField.value.first
//            val semester = _semesterField.value.first
//            val CGPA = _cgpaField.value.first
//            val pernikahan = _pernikahanField.value.first
//            val depresi = _depresiField.value.first
//            val cemas = _kecemasanField.value.first
//            val panic = _panicField.value.first
//            val kebutuhankhusus = _kebutuhankhususField.value.first
//            signUpUseCase.invoke(umur, gender, bidang, semester, CGPA, pernikahan, depresi, cemas, panic, kebutuhankhusus).onFailure { e ->
//                Timber.e(e)
//                _snackbar.emit(e.message ?: "Failed to sign in. Try again later")
//            }
//            _loading.value = false
//        }
//    }
}

