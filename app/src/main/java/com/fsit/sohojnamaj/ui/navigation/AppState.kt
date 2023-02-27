package com.fsit.sohojnamaj.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope


@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
):AppState{
    return remember (navController,coroutineScope){
        AppState(navController)
    }
}
@Stable
class AppState(
    val navController: NavHostController
) {


    fun onBackClick() {

        navController.popBackStack()
    }

}