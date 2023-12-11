package com.ch2ps215.mentorheal.presentation.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.ch2ps215.mentorheal.presentation.common.component.TwoLineDivider
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import com.ch2ps215.mentorheal.presentation.signin.component.NavigateToSignUpButton
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    SignInScreen(
        snackbarHostState = snackbarHostState,
        onChangeEmail = viewModel::changeEmail,
        onChangePassword = viewModel::changePassword,
        emailFieldState = viewModel.emailField,
        passwordFieldState = viewModel.passwordField,
        fulfilledState = viewModel.fulfilled,
        loadingState = viewModel.loading,
        onClickButtonSignIn = viewModel::signIn,
        onAuthGoogle = {},
        onNavigateToSignUpScreen = {
            navController.navigate(Route.SignUp()) {
                popUpTo(Route.SignIn()) { inclusive = true }
            }
        },
    )
}

@Composable
fun SignInScreen(
    snackbarHostState: SnackbarHostState,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    emailFieldState: StateFlow<Pair<String, Int?>>,
    passwordFieldState: StateFlow<Pair<String, Int?>>,
    fulfilledState: StateFlow<Boolean>,
    loadingState: StateFlow<Boolean>,
    onClickButtonSignIn: () -> Unit,
    onAuthGoogle: (String?) -> Unit,
    onNavigateToSignUpScreen: () -> Unit
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
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
                    modifier = Modifier.size(64.dp),
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = stringResource(R.string.welcome),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
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
                    maxLines = 1,
                    singleLine = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null
                        )
                    },
                    isError = emailError != null,
                    label = {
                        Text(text = stringResource(R.string.email))
                    }
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
                    isError = passwordError != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                )
                TextError(
                    textRes = passwordError,
                    modifier = Modifier.align(Alignment.Start)
                )

                val isFulfilled by fulfilledState.collectAsState()

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    enabled = isFulfilled && !isLoading,
                    onClick = onClickButtonSignIn
                ) {
                    Text(text = stringResource(R.string.sign_in))
                }
                TwoLineDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, top = 32.dp, end = 32.dp, bottom = 4.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(R.string.sign_in_alternative),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                NavigateToSignUpButton(
                    modifier = Modifier.padding(vertical = 32.dp),
                    onClick = onNavigateToSignUpScreen
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
fun SignInScreenPreview() {
    MentorhealTheme {
        SignInScreen(
            snackbarHostState = remember { SnackbarHostState() },
            onChangeEmail = { },
            onChangePassword = { },
            emailFieldState = MutableStateFlow("fulan@gmail.com" to null),
            passwordFieldState = MutableStateFlow("password" to null),
            fulfilledState = MutableStateFlow(true),
            loadingState = MutableStateFlow(true),
            onClickButtonSignIn = { },
            onAuthGoogle = { },
            onNavigateToSignUpScreen = { }
        )
    }
}

