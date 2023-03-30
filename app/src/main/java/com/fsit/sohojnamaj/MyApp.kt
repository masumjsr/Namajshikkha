package com.fsit.sohojnamaj

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.annotation.RequiresApi
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.fsit.sohojnamaj.util.LocaleProvider.Companion.init
import com.google.firebase.analytics.FirebaseAnalytics
import com.onesignal.OneSignal
import com.pixplicity.easyprefs.library.Prefs
import com.tanodxyz.gdownload.GDownload
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApp : Application(){
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
    }
    override fun onCreate() {
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

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

    }
}