package com.ch2ps215.mentorheal.presentation.form

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.presentation.common.component.OutlinedTextFieldPassword
import com.ch2ps215.mentorheal.presentation.common.component.TextError
import com.ch2ps215.mentorheal.presentation.common.component.TwoLineDivider
import com.ch2ps215.mentorheal.presentation.form.component.RadioGroup
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import com.ch2ps215.mentorheal.presentation.signin.component.NavigateToSignUpButton
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FormScreen(
    navController: NavHostController,
    viewModel: FormViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackBarHostState::showSnackbar)
    }

    FormScreen(
        snackBarHostState = snackBarHostState,
        usernameState = viewModel.username,
        onChangeUmur = viewModel::changeUmur,
        onChangeGender = viewModel::changeGender,
        onChangeBidang = viewModel::changeBidang,
        onChangeSemester = viewModel::changeSemester,
        onChangeCGPA = viewModel::changeCGPA,
        onChangePernikahan = viewModel::changePernikahan,
        onChangeDepresi = viewModel::changeDepresi,
        onChangeKecemasan = viewModel::changeKecemasan,
        onChangePanic = viewModel::changePanic,
        onChangeKebutuhanKhusus = viewModel::changeKebutuhanKhusus,
        genderFieldState = viewModel.genderField,
        umurFieldState = viewModel.umurField,
        bidangFieldState = viewModel.bidangField,
        semesterFieldState = viewModel.semesterField,
        cgpaFieldState = viewModel.cgpaField,
        pernikahanFieldState = viewModel.pernikahanField,
        depresiFieldState = viewModel.depresiField,
        kecemasanFieldState = viewModel.kecemasanField,
        panicFieldState = viewModel.panicField,
        kebutuhankhususFieldState = viewModel.kebutuhankhususField,
        fulfilledState = viewModel.fulfilled,
        loadingState = viewModel.loading,
//        onClickButtonSignIn = viewModel::signIn,
//        onNavigateToSignUpScreen = {
//            navController.navigate(Route.SignUp()) {
//                popUpTo(Route.SignIn()) { inclusive = true }
//            }
//        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    snackBarHostState: SnackbarHostState,
    usernameState: StateFlow<String?>,
    onChangeUmur: (String) -> Unit,
    onChangeGender: (String) -> Unit,
    onChangeBidang: (String) -> Unit,
    onChangeSemester: (String) -> Unit,
    onChangeCGPA: (String) -> Unit,
    onChangePernikahan: (String) -> Unit,
    onChangeDepresi: (String) -> Unit,
    onChangePanic: (String) -> Unit,
    onChangeKecemasan: (String) -> Unit,
    onChangeKebutuhanKhusus: (String) -> Unit,
    genderFieldState: StateFlow<Pair<String, Int?>>,
    umurFieldState: StateFlow<Pair<String, Int?>>,
    bidangFieldState: StateFlow<Pair<String, Int?>>,
    semesterFieldState: StateFlow<Pair<String, Int?>>,
    cgpaFieldState: StateFlow<Pair<String, Int?>>,
    pernikahanFieldState: StateFlow<Pair<String, Int?>>,
    depresiFieldState: StateFlow<Pair<String, Int?>>,
    panicFieldState: StateFlow<Pair<String, Int?>>,
    kecemasanFieldState: StateFlow<Pair<String, Int?>>,
    kebutuhankhususFieldState: StateFlow<Pair<String, Int?>>,
    fulfilledState: StateFlow<Boolean>,
    loadingState: StateFlow<Boolean>,
//    onClickButtonSubmit: () -> Unit,
//    onNavigateToSignUpScreen: () -> Unit
) {
    Scaffold(
//        modifier = Modifier.systemBarsPadding(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Gray, Color.Gray),
                        startY = 0f,
                        endY = 1f
                    )
                )
        ) {
            val focusManager = LocalFocusManager.current
            val scrollState = rememberScrollState()
            val isLoading by loadingState.collectAsState()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8F8F8))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val umurField by umurFieldState.collectAsState()
                val (umur, umurError) = umurField

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = umur,
                    onValueChange = onChangeUmur,
                    label = {
                        Text(text = stringResource(R.string.umur))
                    },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    isError = umurError != null
                )
                TextError(
                    textRes = umurError,
                    modifier = Modifier.align(Alignment.Start)
                )

                var selectedGender by remember { mutableStateOf<String?>(null) }

                // Radio buttons for gender selection
                RadioGroup(
                    options = listOf(stringResource(id = R.string.laki), stringResource(id = R.string.perempuan)),
                    selectedOption = selectedGender,
                    onOptionSelected = {
                        selectedGender = it
                        onChangeGender(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                val bidangField by bidangFieldState.collectAsState()
                val (bidang, bidangError) = bidangField

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = bidang,
                    onValueChange = onChangeBidang,
                    label = {
                        Text(text = stringResource(R.string.bidang))
                    },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    isError = bidangError != null
                )
                TextError(
                    textRes = bidangError,
                    modifier = Modifier.align(Alignment.Start)
                )

                val semesterField by semesterFieldState.collectAsState()
                val (semester, semesterError) = semesterField

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = semester,
                    onValueChange = onChangeSemester,
                    label = {
                        Text(text = stringResource(R.string.semester))
                    },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    isError = semesterError != null
                )
                TextError(
                    textRes = semesterError,
                    modifier = Modifier.align(Alignment.Start)
                )

                val cgpaField by cgpaFieldState.collectAsState()
                val (cgpa, cgpaError) = cgpaField

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = cgpa,
                    onValueChange = onChangeUmur,
                    label = {
                        Text(text = stringResource(R.string.umur))
                    },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    isError = cgpaError != null
                )
                TextError(
                    textRes = cgpaError,
                    modifier = Modifier.align(Alignment.Start)
                )

                var pernikahan by remember { mutableStateOf<String?>(null) }

                // Dropdown for Yes/No selection
                DropdownMenu(
                    expanded = pernikahan != null,
                    onDismissRequest = { pernikahan = null },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    DropdownMenuItem(
                        onClick = {
                            pernikahan = "Yes"
                            onChangePernikahan("Yes")
                        }
                    ) {
                        Text(text = "Yes")
                    }
                    DropdownMenuItem(
                        onClick = {
                            pernikahan = "No"
                            onChangePernikahan("No")
                        }
                    ) {
                        Text(text = "No")
                    }
                }

                var depresi by remember { mutableStateOf<String?>(null) }

                // Dropdown for Yes/No selection
                DropdownMenu(
                    expanded = depresi != null,
                    onDismissRequest = { depresi = null },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    DropdownMenuItem(
                        onClick = {
                            depresi = "Yes"
                            onChangePernikahan("Yes")
                        }
                    ) {
                        Text(text = "Yes")
                    }
                    DropdownMenuItem(
                        onClick = {
                            depresi = "No"
                            onChangePernikahan("No")
                        }
                    ) {
                        Text(text = "No")
                    }
                }

                var cemas by remember { mutableStateOf<String?>(null) }

                // Dropdown for Yes/No selection
                DropdownMenu(
                    expanded = cemas != null,
                    onDismissRequest = { cemas = null },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    DropdownMenuItem(
                        onClick = {
                            cemas = "Yes"
                            onChangePernikahan("Yes")
                        }
                    ) {
                        Text(text = "Yes")
                    }
                    DropdownMenuItem(
                        onClick = {
                            cemas = "No"
                            onChangePernikahan("No")
                        }
                    ) {
                        Text(text = "No")
                    }
                }

                var panik by remember { mutableStateOf<String?>(null) }

                // Dropdown for Yes/No selection
                DropdownMenu(
                    expanded = panik != null,
                    onDismissRequest = { panik = null },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    DropdownMenuItem(
                        onClick = {
                            panik = "Yes"
                            onChangePernikahan("Yes")
                        }
                    ) {
                        Text(text = "Yes")
                    }
                    DropdownMenuItem(
                        onClick = {
                            panik = "No"
                            onChangePernikahan("No")
                        }
                    ) {
                        Text(text = "No")
                    }
                }


                var khusus by remember { mutableStateOf<String?>(null) }

                // Dropdown for Yes/No selection
                DropdownMenu(
                    expanded = khusus != null,
                    onDismissRequest = { khusus = null },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    DropdownMenuItem(
                        onClick = {
                            khusus = "Yes"
                            onChangePernikahan("Yes")
                        }
                    ) {
                        Text(text = "Yes")
                    }
                    DropdownMenuItem(
                        onClick = {
                            khusus = "No"
                            onChangePernikahan("No")
                        }
                    ) {
                        Text(text = "No")
                    }
                }




                val isFulfilled by fulfilledState.collectAsState()

//                Button(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 16.dp),
//                    enabled = isFulfilled && !isLoading,
//                    onClick = onClickButtonSignIn,
//                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.button))
//
//                ) {
//                    Text(
//                        text = stringResource(R.string.sign_in),
//                        color = Color.White,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//                TwoLineDivider(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(start = 32.dp, top = 32.dp, end = 32.dp, bottom = 4.dp)
//                ) {
//                    Text(
//                        modifier = Modifier.padding(horizontal = 16.dp),
//                        text = stringResource(R.string.sign_in_alternative),
//                        style = MaterialTheme.typography.titleMedium,
//                        color = Color.White
//                    )
//                }
//
//                NavigateToSignUpButton(
//                    modifier = Modifier.padding(vertical = 32.dp),
//                    onClick = onNavigateToSignUpScreen
//                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}



@Preview
@Composable
fun FormScreenPreview() {
    MentorhealTheme {
        FormScreen(
            snackBarHostState = remember { SnackbarHostState() },
            usernameState = MutableStateFlow("Fulan"),
            onChangeUmur = { },
            onChangeGender = { },
            onChangeSemester = { },
            onChangeBidang = { },
            onChangeKebutuhanKhusus = { },
            onChangePanic = { },
            onChangeDepresi = { },
            onChangeCGPA = { },
            onChangePernikahan = { },
            onChangeKecemasan = { },
            umurFieldState = MutableStateFlow("fulan" to null),
            genderFieldState = MutableStateFlow("Laki-Laki" to null),
            bidangFieldState = MutableStateFlow("Laki-Laki" to null),
            semesterFieldState = MutableStateFlow("Laki-Laki" to null),
            cgpaFieldState = MutableStateFlow("Laki-Laki" to null),
            depresiFieldState = MutableStateFlow("Laki-Laki" to null),
            panicFieldState = MutableStateFlow("Laki-Laki" to null),
            pernikahanFieldState = MutableStateFlow("Laki-Laki" to null),
            kecemasanFieldState = MutableStateFlow("Laki-Laki" to null),
            kebutuhankhususFieldState = MutableStateFlow("Laki-Laki" to null),
            fulfilledState = MutableStateFlow(true),
            loadingState = MutableStateFlow(true),

            )
    }
}
