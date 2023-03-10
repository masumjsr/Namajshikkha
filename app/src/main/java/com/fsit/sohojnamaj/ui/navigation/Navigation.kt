package com.fsit.sohojnamaj.ui.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.fsit.sohojnamaj.ui.screen.AyatScreenRoute
import com.fsit.sohojnamaj.ui.screen.HomeScreenRoute
import com.fsit.sohojnamaj.ui.screen.QuranScreenRoute
import com.fsit.sohojnamaj.ui.screen.SettingScreenRoute

const val homeNavigationRoute="home_navigation_route"
const val prayerTimeScreenRoute= "prayer_time_route"
const val settingScreenRoute= "setting_route"
const val quranScreenRoute= "quran_route"
const val ayatScreenRoute= "ayat_route"

internal const val idArg ="id"
internal const val titleArg ="title"

fun NavGraphBuilder.homeScreen(onSettingClick: () -> Unit,onQuranClick: ()->Unit) {
    composable(route = homeNavigationRoute) {
        HomeScreenRoute(onSettingClick=onSettingClick, onQuranClick = onQuranClick)
    }
}

fun NavGraphBuilder.ayatScreen(onBackClick: () -> Unit){
    composable(
        route = "$ayatScreenRoute/{$idArg}/{$titleArg}",
        arguments = listOf(
            navArgument(idArg){type = NavType.IntType},
            navArgument(titleArg){type = NavType.StringType}
        )
    ){
        AyatScreenRoute(onBackClick=onBackClick)

    }
}

fun NavGraphBuilder.settingScreen(onBackClick: () -> Unit) {
    composable(route = settingScreenRoute) {
        SettingScreenRoute(onBackClick=onBackClick)
    }
}
fun NavGraphBuilder.quranScreen(onBackClick: () -> Unit,onSuraClick: (Int,String) -> Unit){
    composable(route= quranScreenRoute){
        QuranScreenRoute(
            onBackClick=onBackClick,
            onSuraClick=onSuraClick
        )
    }
}

fun NavController.navigateToQuran(navOptions: NavOptions?=null) {
    this.navigate(quranScreenRoute,navOptions)
}

fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    this.navigate(settingScreenRoute,navOptions)
}

fun NavController.navigateToAyat(id:Int,title:String,navOptions: NavOptions? = null) {
    this.navigate("$ayatScreenRoute/${id}/${title}")
}