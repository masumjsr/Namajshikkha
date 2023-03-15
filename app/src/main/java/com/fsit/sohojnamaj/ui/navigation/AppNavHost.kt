package com.fsit.sohojnamaj.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        subCategoryScreen (
            onBackClick=onBackClick,
            onTypeOneClick = {
                navController.navigateToTypeOne(it)
                             },
            onTypeTwoClick = {
                navController.navigateToTypeTwo(it)
            }
        )
        homeScreen (
            onSettingClick = { navController.navigateToSetting() },
            onQuranClick = { navController.navigateToQuran()},
            onSubMenuClick = { navController.navigateToSubCategory(it) }
        )
        settingScreen(
            onBackClick=onBackClick
        )
        quranScreen(
            onBackClick = onBackClick,
            onSuraClick = {id,title->navController .navigateToAyat(id,title)}
        )
        ayatScreen(
            onBackClick = onBackClick
        )
        typeOneScreen(
            onBackClick=onBackClick
        )
        typeTwoScreen(
            onBackClick=onBackClick
        )

    }
}