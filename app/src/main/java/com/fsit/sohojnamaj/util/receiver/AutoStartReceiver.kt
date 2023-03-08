package com.fsit.sohojnamaj.util.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fsit.sohojnamaj.data.repository.PrayerRepository
import com.fsit.sohojnamaj.model.AlarmModel
import com.fsit.sohojnamaj.util.dateUtil.toISO8601Date
import com.fsit.sohojnamaj.util.dateUtil.today
import com.fsit.sohojnamaj.util.dateUtil.tomorrow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class AutoStartReceiver : BroadcastReceiver() {
    @Inject
    lateinit var prayerRepository: PrayerRepository
    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            val todayPrayer =  prayerRepository.prayer(today())
            val tomorrowPrayer =  prayerRepository.prayer(tomorrow())

            combine(todayPrayer,tomorrowPrayer){ waqtData, tomorrowPrayer->



                waqtData?.let {waqtData->
                    val alermModel = AlarmModel(
                            fajr = waqtData.Fajr.toISO8601Date(),
                    dhur = waqtData.Dhuhr.toISO8601Date(),
                    asr =waqtData.Asr.toISO8601Date(),
                    magrib = waqtData.Maghrib.toISO8601Date(),
                    isha = waqtData.Isha.toISO8601Date(),
                    nextFajr = tomorrowPrayer?.Fajr.toISO8601Date()
                    )
                  //  PrayTime.schedule(context,alermModel)
                }



            }
        }
    }
}
