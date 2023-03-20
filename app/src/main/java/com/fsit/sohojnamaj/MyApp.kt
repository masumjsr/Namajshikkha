package com.fsit.sohojnamaj

import android.app.Application
import android.content.ContextWrapper
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.fsit.sohojnamaj.util.LocaleProvider.Companion.init
import com.pixplicity.easyprefs.library.Prefs
import com.tanodxyz.gdownload.GDownload
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApp : Application(){
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
        super.onCreate()

    }
}