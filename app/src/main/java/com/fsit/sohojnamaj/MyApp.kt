package com.fsit.sohojnamaj

import android.app.Application
import android.content.ContextWrapper
import com.fsit.sohojnamaj.sync.initializer.Sync
import com.fsit.sohojnamaj.util.LocaleProvider.Companion.init
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel


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
        Sync.initialize(this)
        super.onCreate()

    }
}