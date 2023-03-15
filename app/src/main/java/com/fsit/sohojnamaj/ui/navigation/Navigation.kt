package com.fsit.sohojnamaj.ui.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.fsit.sohojnamaj.ui.screen.*

const val homeNavigationRoute="home_navigation_route"
const val prayerTimeScreenRoute= "prayer_time_route"
const val settingScreenRoute= "setting_route"
const val quranScreenRoute= "quran_route"
const val ayatScreenRoute= "ayat_route"
const val subCategoryRoute="sub_category_route"
const val typeOneRoute="type_one_route"
const val typeTwoRoute="type_two_route"

internal const val idArg ="id"
internal const val titleArg ="title"

fun NavGraphBuilder.homeScreen(
    onSettingClick: () -> Unit,
    onQuranClick: ()->Unit,
    onSubMenuClick: (Int)->Unit,
) {
    composable(route = homeNavigationRoute) {
        HomeScreenRoute(onSettingClick=onSettingClick, onQuranClick = onQuranClick, onSubMenuClick = onSubMenuClick)
    }
}

fun NavGraphBuilder.subCategoryScreen(onBackClick: () -> Unit,
                                      onTypeOneClick:(Int)->Unit,
                                      onTypeTwoClick:(Int)->Unit,){
    composable(
        route="$subCategoryRoute/{$idArg}",
        arguments = listOf(
            navArgument(idArg){type= NavType.IntType}
        )
    ){
        SubCategoryScreenRoute(
            onBackClick = onBackClick,
            onTypeOneClick,onTypeTwoClick

        )

    }
}

fun NavGraphBuilder.typeOneScreen(onBackClick: () -> Unit){
    composable(
        route="$typeOneRoute/{$idArg}",
        arguments = listOf(
            navArgument(idArg){type= NavType.IntType}
        )
    ){
        TypeOneScreenRoute(onBackClick = onBackClick)

    }
}
fun NavGraphBuilder.typeTwoScreen(onBackClick: () -> Unit){
    composable(
        route="$typeTwoRoute/{$idArg}",
        arguments = listOf(
            navArgument(idArg){type= NavType.IntType}
        )
    ){
        TypeTwoScreenRoute(onBackClick = onBackClick)

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
fun NavController.navigateToSubCategory(id:Int){
    this.navigate("$subCategoryRoute/${id}")
}
fun NavController.navigateToTypeOne(id:Int){
    this.navigate("$typeOneRoute/${id}")
}
fun NavController.navigateToTypeTwo(id:Int){
    this.navigate("$typeTwoRoute/${id}")
}

const val testNavigationRoute = "test_navigation_route"



fun NavController.navigateToTest(navOptions: NavOptions? = null){
    this.navigate(testNavigationRoute,navOptions)
}



