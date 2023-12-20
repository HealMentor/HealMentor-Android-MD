package com.ch2ps215.mentorheal.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ch2ps215.mentorheal.presentation.atasikecemasan.AtasiKecemasanScreen
import com.ch2ps215.mentorheal.presentation.detailarticle.DetailArticleScreen
import com.ch2ps215.mentorheal.presentation.form.FormScreen
import com.ch2ps215.mentorheal.presentation.home.HomeScreen
import com.ch2ps215.mentorheal.presentation.kemungkinan.KemungkinanScreen
import com.ch2ps215.mentorheal.presentation.listarticle.ListArticleScreen
import com.ch2ps215.mentorheal.presentation.navgraph.Route.Companion.KEY_ARTICLE_ID
import com.ch2ps215.mentorheal.presentation.navgraph.Route.Companion.KEY_FORM_ID
import com.ch2ps215.mentorheal.presentation.navgraph.Route.Companion.LIST_ARTICLE_TYPE
import com.ch2ps215.mentorheal.presentation.onboarding.OnboardingScreen
import com.ch2ps215.mentorheal.presentation.profile.ProfileScreen
import com.ch2ps215.mentorheal.presentation.signin.SignInScreen
import com.ch2ps215.mentorheal.presentation.signup.SignUpScreen
import com.ch2ps215.mentorheal.presentation.tracker.AddTrackerScreen
import com.ch2ps215.mentorheal.presentation.tracker.DetailTrackerScreen
import com.ch2ps215.mentorheal.presentation.tracker.TrackerScreen
import com.ch2ps215.mentorheal.presentation.twos.TwosScreen

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
            // Buttom Navigation
            composable(Route.Home()) {
                HomeScreen(LocalNavController.current)
            }

            composable(Route.Twos()) {
                TwosScreen(LocalNavController.current)
            }

            composable(Route.Tracker()) {
                TrackerScreen(LocalNavController.current)
            }

            composable(Route.Profile()) {
                ProfileScreen(LocalNavController.current)
            }

            // Home Content
            composable(Route.ListArticle(),
                listOf(
                    navArgument(LIST_ARTICLE_TYPE) {
                        type = NavType.StringType
                    }
                )
            ) {
                ListArticleScreen(LocalNavController.current)
            }

            composable(
                Route.DetailArticle(),
                listOf(
                    navArgument(KEY_ARTICLE_ID) {
                        type = NavType.StringType
                    })
            ) {
                DetailArticleScreen(LocalNavController.current)
            }

            // Detection Content
            composable(Route.Detection()) {
            }

            composable(Route.Form()) {
                FormScreen(LocalNavController.current)
            }

            // Tracker Content
            composable(Route.AddTracker()) {
                AddTrackerScreen(LocalNavController.current)
            }

            composable(Route.DetailTracker()) {
                DetailTrackerScreen(LocalNavController.current)
            }

            composable(Route.Problems(),
                listOf(
                    navArgument(KEY_FORM_ID) {
                        type = NavType.StringType
                    }
                )) {
                KemungkinanScreen(LocalNavController.current)
            }

            composable(Route.AtasiKecemasan()) {
                AtasiKecemasanScreen(LocalNavController.current)
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No NavHostController provided")
}
