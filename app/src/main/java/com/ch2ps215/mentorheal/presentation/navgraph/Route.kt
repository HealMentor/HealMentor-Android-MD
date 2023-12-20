package com.ch2ps215.mentorheal.presentation.navgraph

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.ch2ps215.mentorheal.presentation.listarticle.ListArticleType
import com.ch2ps215.mentorheal.domain.model.Tracker

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
    object AppMainNavigation : Route("appmainnavigation") {
        operator fun invoke() = route
    }

    object Home : Route("home") {
        operator fun invoke() = route
    }

    object Twos : Route("twos") {
        operator fun invoke() = route
    }

    object Tracker : Route("tracker") {
        operator fun invoke() = route
    }

    object Profile : Route("profile") {
        operator fun invoke() = route
    }

    /**
     * This navigation is used to navigate to sub-flows of the home flow.
     */

    // Home Content
    object ListArticle : Route("list_article/{$LIST_ARTICLE_TYPE}") {
        operator fun invoke() = route
        operator fun invoke(listArticle: ListArticleType) = "list_article/${listArticle.name}"
    }

    object DetailArticle : Route("detail_article/{$KEY_ARTICLE_ID}") {

        operator fun invoke() = route
        operator fun invoke(articleId: String) = "detail_article/$articleId"
    }

    // Detection Content
    object Form : Route("form") {
        operator fun invoke() = route
    }

    object Detection : Route("detection") {
        operator fun invoke() = route
    }


    // Tracker Content
    object Problems : Route("kemungkinan") {
        operator fun invoke() = route
    }

    object AtasiKecemasan : Route("atasikecemasan") {
        operator fun invoke() = route
    }

    // Tracker
    object AddTracker : Route("adddata") {
        operator fun invoke() = route
    }

    object DetailTracker : Route("detailtracker") {
        operator fun invoke() = route
    }

    object DetectionCamera : Route("camerammain") {
        operator fun invoke() = route
    }


    companion object {
        const val LIST_ARTICLE_TYPE = "list_article_type"
        const val KEY_ARTICLE_ID = "article_id"
    }
}
