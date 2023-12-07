package com.ch2ps215.mentorheal.presentation.profile

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ch2ps215.mentorheal.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import com.ch2ps215.mentorheal.presentation.profile.component.AppearanceDialog
import com.ch2ps215.mentorheal.presentation.profile.component.Photo
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import trashissue.rebage.presentation.profile.component.ProfileMenuItem
import com.ch2ps215.mentorheal.presentation.profile.component.SignOutDialog

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbar.collectLatest(snackbarHostState::showSnackbar)
    }

    ProfileScreen(
        snackbarHostState = snackbarHostState,
        userState = viewModel.user,
        darkThemeState = viewModel.darkTheme,
        darkTheme = viewModel::darkTheme,
        onSignOut = viewModel::signOut,
        onNavigateToFavoriteArticleScreen = {

        },
        onNavigateToEditProfileScreen = {

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    snackbarHostState: SnackbarHostState,
    userState: StateFlow<User?>,
    darkThemeState: StateFlow<Boolean?>,
    darkTheme: (Boolean?) -> Unit,
    onSignOut: () -> Unit,
    onNavigateToFavoriteArticleScreen: () -> Unit,
    onNavigateToEditProfileScreen: () -> Unit
) {
    var isSignOutDialogOpen by remember { mutableStateOf(false) }
    var isAppearanceDialogOpen by remember { mutableStateOf(false) }
    val isDarkTheme by darkThemeState.collectAsState()

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile),
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            isSignOutDialogOpen = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Logout,
                            contentDescription = stringResource(R.string.cd_sign_out)
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val user by userState.collectAsState()

            Photo(
                modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
                photo = user?.photo,
                onClick = {}
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                text = user?.name ?: "-",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                text = user?.email ?: "-",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            ProfileMenuItem(
                modifier = Modifier
                    .padding(start = 16.dp, top = 32.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                onClick = onNavigateToEditProfileScreen,
                text = stringResource(R.string.edit_profile),
                imageVector = Icons.Outlined.Edit,
                contentDescription = "Edit your profile"
            )
            ProfileMenuItem(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                onClick = {
                    isAppearanceDialogOpen = true
                },
                text = stringResource(R.string.appearance),
                imageVector = when (isDarkTheme ?: isSystemInDarkTheme()) {
                    true -> Icons.Outlined.DarkMode
                    false -> Icons.Outlined.LightMode
                },
                contentDescription = "Change appearance"
            )
        }
    }

    if (isSignOutDialogOpen) {
        SignOutDialog(
            onDismiss = {
                isSignOutDialogOpen = false
            },
            onConfirm = {
                onSignOut()
            }
        )
    }

    if (isAppearanceDialogOpen) {
        var selected by remember(isDarkTheme) { mutableStateOf(isDarkTheme) }

        AppearanceDialog(
            selected = selected,
            onChange = { value ->
                selected = value
            },
            onDismiss = {
                isAppearanceDialogOpen = false
            },
            onConfirm = {
                isAppearanceDialogOpen = false
                darkTheme(selected)
            }
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    MentorhealTheme {
        ProfileScreen(
            snackbarHostState = remember { SnackbarHostState() },
            userState = MutableStateFlow(User("1", "Tubagus", "tubagus@student.ub.ac.id", null, "")),
            darkThemeState = MutableStateFlow(null),
            onSignOut = { },
            darkTheme = { },
            onNavigateToFavoriteArticleScreen = { },
            onNavigateToEditProfileScreen = { }
        )
    }
}