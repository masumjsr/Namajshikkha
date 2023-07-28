package com.fsit.sohojnamaj

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fsit.sohojnamaj.data.Prefs
import com.fsit.sohojnamaj.database.dao.NameDao
import com.fsit.sohojnamaj.ui.navigation.AppNavHost
import com.fsit.sohojnamaj.ui.navigation.AppState
import com.fsit.sohojnamaj.ui.navigation.rememberAppState
import com.fsit.sohojnamaj.ui.theme.NamajShikkhaTheme
import com.fsit.sohojnamaj.ui.viewModel.MainActivityUiState
import com.fsit.sohojnamaj.ui.viewModel.MainActivityViewModel
import com.fsit.sohojnamaj.update.OptionalUpdate
import com.fsit.sohojnamaj.util.BannerAds
import com.fsit.sohojnamaj.util.praytimes.Praytime
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.*
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var suraDao: NameDao
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {



        //createNotification("Hello")
        super.onCreate(savedInstanceState)
        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)
        val splashScreen=installSplashScreen()
        splashScreen.setKeepOnScreenCondition{

                MainActivityUiState.Loading==uiState

            }

        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach {
                        uiState = it
                    }
                    .collect()
            }
        }

        copyAssets(this)
        Praytime.configureForegroundService(this)
        if(intent.getBooleanExtra("stop",false)){
            Log.i("123321", "onCreate: stoping service")
           /* val service =Intent(this,SoundService::class.java)
                stopService(
                    service
                )*/


        }

       else{

           /* val service =Intent(this,SoundService::class.java)
            service.putExtra(Extra.EXTRA_PRAYER,"Maghrib")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(service)
            }
            else{
                startService(service)
            }*/
        }

        //  PrayTime.schedule(this)










        val locale = Locale("bn")
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
        setContent {

            val systemUiController = rememberSystemUiController()
            val darkTheme = shouldUseDarkTheme(uiState)
            NamajShikkhaTheme (darkTheme = darkTheme, dynamicColor = false){
                Locale.setDefault(Locale("bn"))

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val permissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

                    if(!permissionState.status.isGranted){
                        SideEffect {
                            permissionState.launchPermissionRequest()
                        }
                    }

                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OptionalUpdate {
                        MainContent()
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

    }
}

private fun copyAssets(context: Context) {
    val assetManager: AssetManager = context.assets
    var files: Array<String>? = null
    try {
        files = assetManager.list("")
    } catch (e: IOException) {
        Log.e("tag", "Failed to get asset file list.", e)
    }
    if (files != null) for (filename in files) {
        var `in`: InputStream? = null
        var out: OutputStream? = null
        try {
            `in` = assetManager.open(filename)
            val outFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                File(context.dataDir, filename)
            } else {
                File(context.cacheDir, filename)

            }
            out = FileOutputStream(outFile)
            copyFile(`in`, out)
        } catch (e: IOException) {
        } finally {
            if (`in` != null) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    // NOOP
                }
            }
            if (out != null) {
                try {
                    out.close()
                } catch (e: IOException) {
                    // NOOP
                }
            }
        }
    }
}

@Throws(IOException::class)
private fun copyFile(`in`: InputStream, out: OutputStream) {
    val buffer = ByteArray(1024)
    var read: Int
    while (`in`.read(buffer).also { read = it } != -1) {
        out.write(buffer, 0, read)
    }
}

@Composable
fun MainContent(
    appState: AppState = rememberAppState(),

    ) {
    Column(modifier = Modifier) {
        BannerAds(modifier = Modifier.fillMaxWidth())
        AppNavHost(
            navController = appState.navController,
            onBackClick = appState::onBackClick,
            modifier = Modifier.weight(1f)
        )
        //AdmobBanner()
    }
}

val mChannelId = "com.fsit.sohojnamaj.adzan"
private var notificationManager: NotificationManager? = null
val mChannelNameAdzan = "Adzan"
@RequiresApi(Build.VERSION_CODES.O)
private fun createChannel() {
    val importance = NotificationManager.IMPORTANCE_HIGH
    val mChannel = NotificationChannel(mChannelId, mChannelNameAdzan, importance)
    notificationManager?.createNotificationChannel(mChannel)
}

private fun Context.createNotification(text: String) {
    notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
            NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createChannel()
    }

    val notification = NotificationCompat.Builder(applicationContext, mChannelId)
        .setContentTitle("Prayer Time")
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_ALARM)

        .setSound(Uri.parse("android.resource://"+applicationContext.packageName+"/"+ Prefs.muadzin),)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .build()

    notificationManager?.notify(0, notification)
}

@Composable
private fun shouldUseDarkTheme(
    uiState: MainActivityUiState,
): Boolean = when (uiState) {
    MainActivityUiState.Loading -> isSystemInDarkTheme()
    is MainActivityUiState.Success -> {
        Log.i("123321", "shouldUseDarkTheme: user data is ${uiState.userData.darkMode}")
        when (uiState.userData.darkMode) {
            0 -> isSystemInDarkTheme()
            1 -> true
            else -> false
        }
    }

}

