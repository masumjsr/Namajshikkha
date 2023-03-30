package com.fsit.sohojnamaj.util.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fsit.sohojnamaj.data.repository.LocalRepository
import com.fsit.sohojnamaj.data.repository.PrayerSettingRepository
import com.fsit.sohojnamaj.util.praytimes.Praytime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TickReceiver:BroadcastReceiver() {
    @Inject
    lateinit var localRepository: LocalRepository
    @Inject
    lateinit var settingRepository: PrayerSettingRepository

    override fun onReceive(p0: Context?, p1: Intent?) {
      //  Praytime.schedule(p0)
        //PraytimeWidget.update(p0)

        CoroutineScope(Dispatchers.IO).launch {
            combine(localRepository.userData,settingRepository.prayerPreferenceData){ setting, prayer->


                //Praytime.schedule(p0,prayer,setting)



            }.collect()
        }
    }
}