package com.fsit.sohojnamaj.util.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fsit.sohojnamaj.data.repository.LocalRepository
import com.fsit.sohojnamaj.data.repository.PrayerRepository
import com.fsit.sohojnamaj.data.repository.PrayerSettingRepository
import com.fsit.sohojnamaj.model.AlarmModel
import com.fsit.sohojnamaj.util.dateUtil.toISO8601Date
import com.fsit.sohojnamaj.util.dateUtil.today
import com.fsit.sohojnamaj.util.dateUtil.tomorrow
import com.fsit.sohojnamaj.util.praytimes.Praytime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class AutoStartReceiver : BroadcastReceiver() {

    @Inject
    lateinit var localRepository: LocalRepository
    @Inject
    lateinit var settingRepository: PrayerSettingRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            combine(localRepository.userData,settingRepository.prayerPreferenceData){ setting, prayer->


                Praytime.schedule(context,prayer,setting)



            }.collect()
        }
    }
}
