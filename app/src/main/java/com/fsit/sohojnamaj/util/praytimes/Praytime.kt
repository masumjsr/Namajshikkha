package com.fsit.sohojnamaj.util.praytimes

import android.app.Activity
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.fsit.sohojnamaj.PrayerTime
import com.fsit.sohojnamaj.UserPreferences
import com.fsit.sohojnamaj.data.Prefs
import com.fsit.sohojnamaj.model.PrayerPreferenceModel
import com.fsit.sohojnamaj.model.UserData
import com.fsit.sohojnamaj.util.AlarmHelper
import com.fsit.sohojnamaj.util.dateUtil.toDate
import com.fsit.sohojnamaj.util.dateUtil.toDateFormat
import com.fsit.sohojnamaj.util.dateUtil.toTimeFormat
import com.fsit.sohojnamaj.util.receiver.AdzanReceiver
import com.fsit.sohojnamaj.util.receiver.TickReceiver
import java.util.*

class Praytime {
    
    companion object{
        fun configureForegroundService(activity: Activity?){
            activity?.apply {
                registerReceiver(TickReceiver(), IntentFilter(Intent.ACTION_TIME_TICK))
                registerReceiver(TickReceiver(), IntentFilter(Intent.ACTION_TIME_CHANGED))
            }
        }
        fun schedule(context:Context?,prayerPreferenceModel: PrayerPreferenceModel,userPreferences: UserData,source:String){

         /*  context?.apply{
               // PraytimeWidget.update(this)
               // Khatam.schedule(this)
               // DailyReminder.schedule(this)
                val fajr=userPreferences.fajr.toDate()
                val dhur=userPreferences.dhur.toDate()
                val asr=userPreferences.asr.toDate()
                val magrib=userPreferences.maghrib.toDate()
                val isha=userPreferences.isha.toDate()
                val nextFajr=userPreferences.nextFajr.toDate()

                if (prayerPreferenceModel.latLng.latitude == 0.0 || prayerPreferenceModel.latLng.longitude == 0.0) {
                    return
                }

                val soundModel= prayerPreferenceModel.soundModel


                if (System.currentTimeMillis()<fajr) {
                    configureAdzanScheduler(
                        fajr,
                        AlarmHelper.mFajrRequestCode,
                        PraytimeType.Fajr,
                        soundModel.fajr
                    )
                }

                if (System.currentTimeMillis()<dhur) {
                    configureAdzanScheduler(
                        dhur,
                        AlarmHelper.mDhuhrRequestCode,
                        PraytimeType.Dhuhr,
                        soundModel.dhur
                    )
                }

                if (System.currentTimeMillis()<asr) {
                    configureAdzanScheduler(
                        asr,
                        AlarmHelper.mAsrRequestCode,
                        PraytimeType.Asr,
                        soundModel.asr
                    )
                }

                if (System.currentTimeMillis()<magrib) {
                    configureAdzanScheduler(
                        magrib,
                        AlarmHelper.mMaghribRequestCode,
                        PraytimeType.Maghrib,
                        soundModel.magrib
                    )
                }

                if (System.currentTimeMillis()<isha) {
                    configureAdzanScheduler(
                        isha,
                        AlarmHelper.mIsyaRequestCode,
                        PraytimeType.Isya,
                        soundModel.isha
                    )
                }
               if (System.currentTimeMillis()<nextFajr) {
                   configureAdzanScheduler(
                       fajr,
                       AlarmHelper.mFajrRequestCode,
                       PraytimeType.Fajr,
                       soundModel.fajr
                   )
               }
            }*/
        }

        private fun Context.configureAdzanScheduler(triggerTime: Long, requestCode: Int, prayer: PraytimeType, ringType: Int) {
            val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent = AdzanReceiver.getPendingIntent(this, requestCode, triggerTime, prayer, ringType)
            alarmMgr.cancel(alarmIntent)
            Log.i("123321", "configureAdzanScheduler: ${prayer.name} trigger time: ${triggerTime.toDateFormat()}")

            alarmMgr.setAlarmClock(AlarmManager.AlarmClockInfo(triggerTime, null), alarmIntent)
        }

        fun getFajrTime(prayTime: PrayerTime): Calendar {
            return Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, prayTime.fajr?.split(":")?.get(0)?.toInt() ?: 4)
                set(Calendar.MINUTE, prayTime.fajr?.split(":")?.get(1)?.toInt() ?: 4)
                set(Calendar.SECOND, 5)
                set(Calendar.MILLISECOND, 0)
            }
        }

        fun getDhuhrTime(prayTime: PrayerTime): Calendar {
            return Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, prayTime.dhuhr?.split(":")?.get(0)?.toInt() ?: 11)
                set(Calendar.MINUTE, prayTime.dhuhr?.split(":")?.get(1)?.toInt() ?: 34)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        }

        fun getAsrTime(prayTime: PrayerTime): Calendar {
            return Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, prayTime.asr?.split(":")?.get(0)?.toInt() ?: 14)
                set(Calendar.MINUTE, prayTime.asr?.split(":")?.get(1)?.toInt() ?: 49)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        }

        fun getMaghribTime(prayTime: PrayerTime): Calendar {
            return Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, prayTime.maghrib?.split(":")?.get(0)?.toInt() ?: 17)
                set(Calendar.MINUTE, prayTime.maghrib?.split(":")?.get(1)?.toInt() ?: 44)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
        }

        fun getIsyaTime(prayTime: PrayerTime): Calendar {
            return Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, prayTime.isya?.split(":")?.get(0)?.toInt() ?: 18)
                set(Calendar.MINUTE, prayTime.isya?.split(":")?.get(1)?.toInt() ?: 56)
                set(Calendar.SECOND, 10)
                set(Calendar.MILLISECOND, 0)
            }
        }
    }


}

enum class PraytimeType {
    Refresh,
    Loop,
    Fajr,
    Dhuhr,
    Asr,
    Maghrib,
    Isya
}