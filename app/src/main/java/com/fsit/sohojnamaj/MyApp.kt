package com.fsit.sohojnamaj

import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.fsit.sohojnamaj.util.ADMOB_APP_OPEN_ID
import com.fsit.sohojnamaj.util.LocaleProvider.Companion.init
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import com.onesignal.OneSignal
import com.pixplicity.easyprefs.library.Prefs
import com.tanodxyz.gdownload.GDownload
import dagger.hilt.android.HiltAndroidApp
import java.util.*


@HiltAndroidApp
class MyApp : Application(){


    private var currentActivity: Activity? = null

    private  val LOG_TAG = "123321"
    val mChannelId = "com.fsit.sohojnamaj.adzan"
    private var notificationManager: NotificationManager? = null
    val mChannelNameAdzan = "Adzan"
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(mChannelId, mChannelNameAdzan, importance)



        notificationManager?.createNotificationChannel(mChannel)

        val channelId = "KaziTV"
        val notificationManager = getSystemService(
            NOTIFICATION_SERVICE
        ) as NotificationManager
        // Check if the Android Version is greater than Oreo
        // Check if the Android Version is greater than Oreo
        if (Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.O
        ) {
            val notificationChannel = NotificationChannel(
                channelId, channelId,
                NotificationManager.IMPORTANCE_HIGH
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                notificationChannel.setAllowBubbles(true)
            }
            notificationChannel.enableLights(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.importance = NotificationManager.IMPORTANCE_HIGH
            notificationChannel.enableLights(true)
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
        try {
            FirebaseMessaging.getInstance().subscribeToTopic("all")
        } catch (_: Exception) {
        }
    }
    override fun onCreate() {
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()



        //  RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("0B7CAF51CFF07FF147EE0C730E31AC21"))

//Optional if you want to add test device
       val configuration = RequestConfiguration.Builder().setTestDeviceIds(listOf("0B7CAF51CFF07FF147EE0C730E31AC21"))
           .build()


        Log.i("123321", "onCreate: ${configuration.testDeviceIds}")
        
        MobileAds.setRequestConfiguration(configuration)

        init(this)
        CaocConfig.Builder.create()
            .errorActivity(CustomErrorActivity::class.java)
            .apply()
        //Sync.initialize(this)
        GDownload.init()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val ONESIGNAL_APP_ID = "fbd6d69a-8a27-483b-8b83-b1bc5b3965a2"


        /* ------------------------------------------------------
                Onesignal
---------------------------------------------------------*/
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        FirebaseAnalytics.getInstance(this);

        super.onCreate()
      //  registerActivityLifecycleCallbacks(this)
//
        // Log the Mobile Ads SDK version.
        Log.d(LOG_TAG, "Google Mobile Ads SDK Version: " + MobileAds.getVersion())

        MobileAds.initialize(this) {}
        AppOpenAdManager(this, ADMOB_APP_OPEN_ID)
      //  ProcessLifecycleOwner.get().lifecycle.addObserver(this)
      //  appOpenAdManager = AppOpenAdManager()

    }



}

