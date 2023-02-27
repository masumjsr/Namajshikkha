package com.fsit.sohojnamaj.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.fsit.sohojnamaj.ui.screen.HomeScreenRoute

const val homeNavigationRoute="home_navigation_route"

fun NavGraphBuilder.homeScreen() {
    composable(route = homeNavigationRoute) {
        HomeScreenRoute()
    }
}