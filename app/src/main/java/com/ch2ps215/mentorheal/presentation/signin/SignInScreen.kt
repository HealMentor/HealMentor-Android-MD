package com.ch2ps215.mentorheal.presentation.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.*
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
import androidx.core.content.res.ResourcesCompat
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
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackBarHostState::showSnackbar)
    }

    SignInScreen(
        snackBarHostState = snackBarHostState,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    snackBarHostState: SnackbarHostState,
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
            val context = LocalContext.current

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Image in the middle of the screen
                Image(
                    painter = painterResource(id = R.drawable.login),
                    contentDescription = null,
                    modifier = Modifier
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF24B0C1),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
                    .align(Alignment.BottomStart),
            ) {

                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.welcome),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
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
                    onClick = onClickButtonSignIn,
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.button))

                ) {
                    Text(
                        text = stringResource(R.string.sign_in),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
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
                        color = Color.White
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
            snackBarHostState = remember { SnackbarHostState() },
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

