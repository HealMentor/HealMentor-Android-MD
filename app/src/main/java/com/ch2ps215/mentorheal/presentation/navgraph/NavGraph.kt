package com.ch2ps215.mentorheal.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ch2ps215.mentorheal.presentation.detection.DetectionScreen
import com.ch2ps215.mentorheal.presentation.home.HomeScreen
import com.ch2ps215.mentorheal.presentation.onboarding.OnboardingScreen
import com.ch2ps215.mentorheal.presentation.signin.SignInScreen
import com.ch2ps215.mentorheal.presentation.signup.SignUpScreen
import com.ch2ps215.mentorheal.presentation.tracker.TrackerScreen
import com.ch2ps215.mentorheal.presentation.profile.ProfileScreen

@Composable
fun NavGraph(
    startDestination: String,
    navController: NavHostController,
) {

    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        navigation(
            route = Route.AppStartNavigation(),
            startDestination = Route.Onboarding()
        ) {
            composable(Route.Onboarding()) {
                OnboardingScreen(LocalNavController.current)
            }
        }

        navigation(
            route = Route.AppAuthNavigation(),
            startDestination = Route.SignIn()
        ) {

            composable(Route.SignIn()) {
                SignInScreen(LocalNavController.current)
            }

            composable(Route.SignUp()) {
                SignUpScreen(LocalNavController.current)
            }
        }

        navigation(
            route = Route.AppMainNavigation(),
            startDestination = Route.Home()
        ) {
            composable(Route.Home()) {
                HomeScreen(LocalNavController.current)
            }

            composable(Route.Detection()) {
                DetectionScreen(LocalNavController.current)
            }

            composable(Route.Tracker()) {
                TrackerScreen(LocalNavController.current)
            }

            composable(Route.Profile()) {
                ProfileScreen(LocalNavController.current)
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavHostController provided")
}