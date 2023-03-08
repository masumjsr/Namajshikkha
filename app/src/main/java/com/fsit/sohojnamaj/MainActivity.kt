package com.fsit.sohojnamaj

import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fsit.sohojnamaj.ui.navigation.AppNavHost
import com.fsit.sohojnamaj.ui.navigation.AppState
import com.fsit.sohojnamaj.ui.navigation.rememberAppState
import com.fsit.sohojnamaj.ui.theme.NamajShikkhaTheme
import com.fsit.sohojnamaj.util.praytimes.PrayerTimeHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        volumeControlStream = AudioManager.STREAM_ALARM

        //  PrayTime.schedule(this)




        val locale = Locale("bn")
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
        setContent {
            NamajShikkhaTheme (darkTheme = false,dynamicColor = false){
                Locale.setDefault(Locale("bn"))
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent(
    appState: AppState = rememberAppState(),

    ) {
    AppNavHost(
        navController = appState.navController,
        onBackClick = appState::onBackClick
    )
}


