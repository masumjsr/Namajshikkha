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
        subCategoryScreen(
            onBackClick = onBackClick,
            onTypeOneClick = { id, title ->
                navController.navigateToTypeOne(id, title)
            },
            onTypeTwoClick = { id, title ->
                navController.navigateToTypeTwo(id, title)
            },
            onAlifBaaScreen = { navController.navigateToAlifBaScreen() },
            onNuktaClick = { navController.navigateToNukta() }
        )
        homeScreen(
            onSettingClick = { navController.navigateToSetting() },
            onQuranClick = { navController.navigateToQuran() },
            onSortQuranClick = { navController.navigateToSortQuran() },
            onSubMenuClick = { navController.navigateToSubCategory(it) },
            onTasbhiClick = { navController.navigateToTasbhi() },
            onNameClick = { navController.navigateToName() },
            onCompassScreen = { navController.navigateToCompassScreen() },
            onZakatClick = { navController.navigateToZakat()}
        )
        settingScreen(
            onBackClick = onBackClick
        )
        quranScreen(
            onBackClick = onBackClick,
            onSuraClick = { id, title -> navController.navigateToAyat(id, title) }
        )
        sortQuranScreen(
            onBackClick = onBackClick,
            onSuraClick = { id, title -> navController.navigateToAyat(id, title) }
        )
        ayatScreen(
            onBackClick = onBackClick
        )
        typeOneScreen(
            onBackClick = onBackClick
        )
        typeTwoScreen(
            onBackClick = onBackClick
        )
        nameScreen(onBackClick)
        compassScreen(onBackClick)
        tasbhiScreen(onBackClick)
        alifBaScreen(onBackClick)
        nuktaScreen { onBackClick.invoke() }
        zakatScreen(onBackClick)


    }
}