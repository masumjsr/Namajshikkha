package com.fsit.sohojnamaj.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fsit.sohojnamaj.ui.screen.HomeScreenRoute
import com.fsit.sohojnamaj.ui.screen.SettingScreenRoute

const val homeNavigationRoute="home_navigation_route"
const val prayerTimeScreenRoute= "prayer_time_route"
const val settingScreenRoute= "setting_route"

fun NavGraphBuilder.homeScreen(onSettingClick: () -> Unit) {
    composable(route = homeNavigationRoute) {
        HomeScreenRoute(onSettingClick=onSettingClick)
    }
}

fun NavGraphBuilder.settingScreen(onBackClick: () -> Unit) {
    composable(route = settingScreenRoute) {
        SettingScreenRoute(onBackClick=onBackClick)
    }
}

fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    this.navigate(settingScreenRoute,navOptions)
}