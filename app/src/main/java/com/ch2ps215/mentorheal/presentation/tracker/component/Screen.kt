package com.ch2ps215.mentorheal.presentation.tracker.component

sealed class Screen(val route: String) {
    object Tracker : Screen("tracker")
    object Detail : Screen("detail/{title}/{description}/{rating}")
    object AddTracker : Screen("adddata")
}
