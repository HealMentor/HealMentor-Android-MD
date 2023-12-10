package com.ch2ps215.mentorheal.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.ch2ps215.mentorheal.presentation.theme.MentorhealTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val isDarkTheme by viewModel.darkTheme.collectAsState()
            val navController = rememberNavController()

            MentorhealTheme(
                dynamicColor = false,
                darkTheme = isDarkTheme ?: isSystemInDarkTheme()
            ) {

                /*LaunchedEffect(isLight) {
                    navController.currentBackStackEntryFlow.collectLatest { backStack ->
                        if (backStack.destination.route == Route.Home()) {
                            systemUiController.setStatusBarColor(
                                color = Color.Transparent,
                                darkIcons = false
                            )
                        } else {
                            systemUiController.setStatusBarColor(
                                color = Color.Transparent,
                                darkIcons = isLight
                            )
                        }
                    }
                }*/

                MainScreen(
                    onboardingState = viewModel.onboarding,
                    loggedInState = viewModel.loggedIn,
                    navController = navController
                )
            }
        }
    }
}
