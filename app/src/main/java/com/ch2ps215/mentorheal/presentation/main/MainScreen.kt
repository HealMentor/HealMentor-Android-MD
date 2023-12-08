package com.ch2ps215.mentorheal.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DocumentScanner
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ch2ps215.mentorheal.R
import com.ch2ps215.mentorheal.presentation.main.component.NavigationBarMain
import com.ch2ps215.mentorheal.presentation.navgraph.LocalNavController
import com.ch2ps215.mentorheal.presentation.navgraph.NavGraph
import com.ch2ps215.mentorheal.presentation.navgraph.Route
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import kotlin.collections.contains

val BotNavMenus = listOf(
    Triple(R.string.home, Icons.Outlined.Home to Icons.Filled.Home, Route.Home()),
    Triple(
        R.string.detection,
        Icons.Outlined.DocumentScanner to Icons.Filled.DocumentScanner,
        Route.Detection()
    ),
    Triple(R.string.tracker, Icons.Outlined.Timeline to Icons.Filled.Timeline, Route.Tracker()),
    Triple(
        R.string.profile,
        Icons.Outlined.AccountCircle to Icons.Filled.AccountCircle,
        Route.Profile()
    ),
)

@Composable
fun MainScreen(
    navController: NavHostController,
    onboardingState: StateFlow<Boolean?>,
    loggedInState: StateFlow<Boolean?>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val currentBackStack by navController.currentBackStackEntryAsState()
        val isBotNavVisible by remember {
            val screenWithBotNav = BotNavMenus.map { it.third }

            derivedStateOf {
                when (currentBackStack?.destination?.route) {
                    in screenWithBotNav -> true
                    else -> false
                }
            }
        }

        Column(modifier = Modifier.weight(1F)) {
            CompositionLocalProvider(LocalNavController provides navController) {
                val isAlreadyOnboarding by onboardingState.collectAsState()
                val isLoggedIn by loggedInState.collectAsState()

                SideEffect {
                    Timber.d("IS LOGGED IN $isLoggedIn")
                    Timber.d("IS ALREADY ONBOARDING $isAlreadyOnboarding")
                }

                AnimatedVisibility(
                    visible = isAlreadyOnboarding != null && isLoggedIn != null,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    NavGraph(
                        startDestination = when (isAlreadyOnboarding) {
                            false -> Route.AppStartNavigation()
                            else -> when (isLoggedIn) {
                                false -> Route.AppAuthNavigation()
                                else -> Route.AppMainNavigation()
                            }
                        },
                        navController = navController
                    )
                }
            }
        }
        NavigationBarMain(
            onNavigation = { route ->
                navController.navigate(route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(Route.Home()) { saveState = true }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            },
            botNavVisibilityProvider = { isBotNavVisible },
            currentRouteProvider = { route ->
                currentBackStack?.destination?.hierarchy?.any { it.route == route } == true
            }
        )
    }
}
