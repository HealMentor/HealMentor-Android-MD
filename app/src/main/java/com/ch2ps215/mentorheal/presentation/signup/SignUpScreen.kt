package com.ch2ps215.mentorheal.presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
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
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import com.ch2ps215.mentorheal.presentation.signup.component.NavigateToSignInButton
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackBarHostState::showSnackbar)
    }

    SignUpScreen(
        snackBarHostState = snackBarHostState,
        onChangeName = viewModel::changeName,
        onChangeEmail = viewModel::changeEmail,
        onChangePassword = viewModel::changePassword,
        onChangeConfirmPassword = viewModel::changeConfirmPassword,
        nameFieldState = viewModel.nameField,
        emailFieldState = viewModel.emailField,
        passwordFieldState = viewModel.passwordField,
        confirmPasswordFieldState = viewModel.confirmPasswordField,
        fulfilledState = viewModel.fulfilled,
        loadingState = viewModel.loading,
        onClickButtonSignUp = viewModel::signUp,
        onAuthGoogle = {},
        onNavigateToSignInScreen = {
            navController.navigate(Route.SignIn()) {
                popUpTo(Route.SignUp()) { inclusive = true }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    snackBarHostState: SnackbarHostState,
    onChangeName: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onChangeConfirmPassword: (String) -> Unit,
    nameFieldState: StateFlow<Pair<String, Int?>>,
    emailFieldState: StateFlow<Pair<String, Int?>>,
    passwordFieldState: StateFlow<Pair<String, Int?>>,
    confirmPasswordFieldState: StateFlow<Pair<String, Int?>>,
    fulfilledState: StateFlow<Boolean>,
    loadingState: StateFlow<Boolean>,
    onClickButtonSignUp: () -> Unit,
    onAuthGoogle: (String?) -> Unit,
    onNavigateToSignInScreen: () -> Unit
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val focusManager = LocalFocusManager.current
            val scrollState = rememberScrollState()
            val isLoading by loadingState.collectAsState()

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    text = stringResource(R.string.welcome),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                val nameField by nameFieldState.collectAsState()
                val (name, nameError) = nameField

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = name,
                    onValueChange = onChangeName,
                    label = {
                        Text(text = stringResource(R.string.name))
                    },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    isError = nameError != null
                )
                TextError(
                    textRes = nameError,
                    modifier = Modifier.align(Alignment.Start)
                )

                val emailField by emailFieldState.collectAsState()
                val (email, emailError) = emailField

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = email,
                    onValueChange = onChangeEmail,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = stringResource(R.string.email))
                    },
                    maxLines = 1,
                    singleLine = true,
                    isError = emailError != null
                )
                TextError(
                    textRes = emailError,
                    modifier = Modifier.align(Alignment.Start)
                )

                val passwordField by passwordFieldState.collectAsState()
                val (password, passwordError) = passwordField

                OutlinedTextFieldPassword(
                    value = password,
                    onValueChange = onChangePassword,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    isError = passwordError != null
                )
                TextError(
                    textRes = passwordError,
                    modifier = Modifier.align(Alignment.Start)
                )

                val confirmPasswordField by confirmPasswordFieldState.collectAsState()
                val (confirmPassword, confirmPasswordError) = confirmPasswordField

                OutlinedTextFieldPassword(
                    value = confirmPassword,
                    onValueChange = onChangeConfirmPassword,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    label = stringResource(R.string.confirm_password),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    isError = confirmPasswordError != null
                )
                TextError(
                    textRes = confirmPasswordError,
                    modifier = Modifier.align(Alignment.Start)
                )

                val isFulfilled by fulfilledState.collectAsState()

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    enabled = isFulfilled && !isLoading,
                    onClick = onClickButtonSignUp
                ) {
                    Text(text = stringResource(R.string.sign_up))
                }

                NavigateToSignInButton(
                    modifier = Modifier.padding(vertical = 32.dp),
                    onClick = onNavigateToSignInScreen
                )
            }

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    MentorhealTheme {
        SignUpScreen(
            snackBarHostState = remember { SnackbarHostState() },
            onChangeName = { },
            onChangeEmail = { },
            onChangePassword = { },
            onChangeConfirmPassword = { },
            nameFieldState = MutableStateFlow("fulan" to null),
            emailFieldState = MutableStateFlow("fulan@gmail.com" to null),
            passwordFieldState = MutableStateFlow("secret" to null),
            confirmPasswordFieldState = MutableStateFlow("secret" to null),
            fulfilledState = MutableStateFlow(true),
            loadingState = MutableStateFlow(true),
            onClickButtonSignUp = { },
            onAuthGoogle = { },
            onNavigateToSignInScreen = { }
        )
    }
}
