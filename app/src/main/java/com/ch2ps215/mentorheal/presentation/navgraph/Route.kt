package com.ch2ps215.mentorheal.presentation.navgraph

sealed class Route(
    protected val route: String
) {
    /**
     * AppStartNavigation is the navigation that is shown when the app is first opened.
     * This navigation is only shown once, and is used to navigate to the onboarding flow.
     */
    object AppStartNavigation : Route("appstartnavigation") {
        operator fun invoke() = route
    }

    object Onboarding : Route("onboarding") {
        operator fun invoke() = route
    }

    /**
     * AppAuthNavigation is the navigation that is shown after the user has completed the onboarding flow.
     * This navigation is used to navigate to the sign in and sign up flows.
     */
    object AppAuthNavigation : Route("appauthnavigation") {
        operator fun invoke() = route
    }

    object SignIn : Route("sign_in") {
        operator fun invoke() = route
    }

    object SignUp : Route("sign_up") {
        operator fun invoke() = route
    }

    /**
     * Home is the navigation that is shown after the user has signed in.
     * This navigation is used to navigate to the home flow.
     */
    object AppMainNavigation : Route("appmainavigation") {
        operator fun invoke() = route
    }

    object Home : Route("home") {
        operator fun invoke() = route
    }

    object Detection : Route("detection") {
        operator fun invoke() = route
    }

    object Tracker : Route("tracker") {
        operator fun invoke() = route
    }

    object Profile : Route("profile") {
        operator fun invoke() = route
    }

    object Form : Route("form") {
        operator fun invoke() = route
    }

    object Problems : Route("kemungkinan") {
        operator fun invoke() = route
    }

    object AtasiKecemasan : Route("atasikecemasan") {
        operator fun invoke() = route
    }


    companion object {
        const val LIST_ARTICLE_TYPE = "list_article_type"
    }
}
