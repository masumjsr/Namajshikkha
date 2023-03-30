package com.fsit.sohojnamaj.ui.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.fsit.sohojnamaj.ui.screen.*

const val homeNavigationRoute="home_navigation_route"
const val prayerTimeScreenRoute= "prayer_time_route"
const val settingScreenRoute= "setting_route"
const val quranScreenRoute= "quran_route"
const val sortQuranScreenRoute= "sort_quran_route"
const val ayatScreenRoute= "ayat_route"
const val subCategoryRoute="sub_category_route"
const val typeOneRoute="type_one_route"
const val typeTwoRoute="type_two_route"
const val tasbiScreenRoute="tasbi_route"



const val nameScreenRoute="name_route"
fun NavGraphBuilder.nameScreen(
    onBackClick: () -> Unit
){
    composable(route= nameScreenRoute){
        NameScreenRoute(onBackClick = onBackClick)
    }
}
fun NavController.navigateToName(navOptions: NavOptions? = null){
    this.navigate(nameScreenRoute,navOptions)
}




const val compassScreenRoute="compass_route"
const val alifBaScreenRoute="alif_ba_route"


internal const val idArg ="id"
internal const val titleArg ="title"

fun NavGraphBuilder.homeScreen(
    onSettingClick: () -> Unit,
    onQuranClick: ()->Unit,
    onSortQuranClick: ()->Unit,
    onSubMenuClick: (Int)->Unit,
    onTasbhiClick: ()->Unit,
    onNameClick: ()->Unit,
    onCompassScreen: ()->Unit,
    onZakatClick: ()->Unit,
    onDonationClick: ()->Unit,

    ) {
    composable(route = homeNavigationRoute) {
        HomeScreenRoute(
            onSettingClick=onSettingClick,
            onQuranClick = onQuranClick,
            onSortQuranClick = onSortQuranClick,
            onSubMenuClick = onSubMenuClick,
            onTasbhiClick = onTasbhiClick,
            onNameClick=onNameClick,
            onCompassScreen=onCompassScreen,
            onZakatClick=onZakatClick,
            onDonationClick=onDonationClick,
        )
    }
}
fun NavGraphBuilder.compassScreen(
    onBackClick: () -> Unit
)
{
    composable(route= compassScreenRoute){
        CompassScreenRoute(onBackClick)
    }
}

fun NavGraphBuilder.alifBaScreen(
    onBackClick: () -> Unit
){
    composable(route= alifBaScreenRoute){
        AlifBaScreenRoute(onBackClick)
    }
}



fun NavController.navigateToAlifBaScreen(navOptions: NavOptions?=null){
    this.navigate(alifBaScreenRoute,navOptions)
}


fun NavGraphBuilder.tasbhiScreen(
    onBackClick: () -> Unit
){
    composable(route = tasbiScreenRoute){
        TasbhiScreenRoute(onBackClick=onBackClick)
    }
}

fun NavGraphBuilder.subCategoryScreen(
    onBackClick: () -> Unit,
    onTypeOneClick:(Int,String)->Unit,
    onTypeTwoClick:(Int,String)->Unit,
    onAlifBaaScreen:()->Unit,
    onNuktaClick:()->Unit
){
    composable(
        route="$subCategoryRoute/{$idArg}",
        arguments = listOf(
            navArgument(idArg){type= NavType.IntType}
        )
    ){
        SubCategoryScreenRoute(
            onBackClick = onBackClick,
            onTypeOneClick,onTypeTwoClick,
            onAlifBaaScreen=onAlifBaaScreen,
            onNuktaScreen = onNuktaClick
        )

    }
}

fun NavGraphBuilder.typeOneScreen(onBackClick: () -> Unit){
    composable(
        route="$typeOneRoute/{$idArg}/{$titleArg}",
        arguments = listOf(

            navArgument(idArg){type = NavType.IntType},
            navArgument(titleArg){type = NavType.StringType}
        )
    ){
        TypeOneScreenRoute(onBackClick = onBackClick)

    }
}
fun NavGraphBuilder.typeTwoScreen(onBackClick: () -> Unit){
    composable(
        route="$typeTwoRoute/{$idArg}/{$titleArg}",
        arguments = listOf(

            navArgument(idArg){type = NavType.IntType},
            navArgument(titleArg){type = NavType.StringType}
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

fun NavGraphBuilder.sortQuranScreen(onBackClick: () -> Unit,onSuraClick: (Int,String) -> Unit){
    composable(route= sortQuranScreenRoute){
        SortQuranScreenRoute(
            onBackClick=onBackClick,
            onSuraClick=onSuraClick
        )
    }
}

fun NavController.navigateToQuran(navOptions: NavOptions?=null) {
    this.navigate(quranScreenRoute,navOptions)
}

fun NavController.navigateToSortQuran(navOptions: NavOptions?=null) {
    this.navigate(sortQuranScreenRoute,navOptions)
}

fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    this.navigate(settingScreenRoute,navOptions)
}

fun NavController.navigateToTasbhi(navOptions: NavOptions? = null) {
    this.navigate(tasbiScreenRoute,navOptions)
}

fun NavController.navigateToAyat(id:Int,title:String,navOptions: NavOptions? = null) {
    this.navigate("$ayatScreenRoute/${id}/${title}")
}
fun NavController.navigateToSubCategory(id:Int){
    this.navigate("$subCategoryRoute/${id}")
}
fun NavController.navigateToTypeOne(id: Int, title: String){
    this.navigate("$typeOneRoute/${id}/${title}")
}
fun NavController.navigateToTypeTwo(id: Int, title: String){
    this.navigate("$typeTwoRoute/${id}/${title}")
}

const val testNavigationRoute = "test_navigation_route"



fun NavController.navigateToTest(navOptions: NavOptions? = null){
    this.navigate(testNavigationRoute,navOptions)
}


fun NavController.navigateToCompassScreen(navOptions: NavOptions? = null){
    this.navigate(compassScreenRoute,navOptions)
}


const val nuktaScreenRoute="nukta_route"
fun NavGraphBuilder.nuktaScreen(
    onBackClick: () -> Unit
){
    composable(route= nuktaScreenRoute){
        NuktaScreenRoute(onBackClick = onBackClick)
    }
}
fun NavController.navigateToNukta(navOptions: NavOptions? = null){
    this.navigate(nuktaScreenRoute,navOptions)
}
const val zakatScreenRoute="zakat_route"
fun NavGraphBuilder.zakatScreen(
    onBackClick: () -> Unit
){
    composable(route= zakatScreenRoute){
        ZakatScreenRoute(onBackClick = onBackClick)
    }
}
fun NavController.navigateToZakat(navOptions: NavOptions? = null){
    this.navigate(zakatScreenRoute,navOptions)
}

const val DonationScreenRoute="Donation_route"
fun NavGraphBuilder.DonationScreen(
    onBackClick: () -> Unit
){
    composable(route= DonationScreenRoute){
        DonationScreenRoute(onBackClick = onBackClick)
    }
}
fun NavController.navigateToDonationScreenRoute(navOptions: NavOptions? = null){
    this.navigate(DonationScreenRoute,navOptions)
}
