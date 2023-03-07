package com.fsit.sohojnamaj.util.praytimes

import android.app.AlarmManager
import android.content.Context
import android.util.Log
import com.fsit.sohojnamaj.data.Prefs
import com.fsit.sohojnamaj.model.AlarmModel
import com.fsit.sohojnamaj.util.AlarmHelper
import com.fsit.sohojnamaj.util.dateUtil.toDateFormat
import com.fsit.sohojnamaj.util.receiver.AdzanReceiver

class PrayTime{
    companion object {
        fun schedule(context: Context?, prayTime: AlarmModel) {
            Log.i("123321", "schedule: setting alerm")

            val systemTime = System.currentTimeMillis()
            if (systemTime<prayTime.fajr) {
                Log.i("123321", "schedule: alerm set for fajr")
                context?.configureAdzanScheduler(
                    prayTime.fajr,
                    AlarmHelper.mFajrRequestCode,
                    PraytimeType.Fajr,
                    Prefs.ringFajr
                )
            }

            if (systemTime<prayTime.dhur)
                context?.configureAdzanScheduler(
                prayTime.dhur,
                AlarmHelper.mDhuhrRequestCode,
                PraytimeType.Dhuhr,
                Prefs.ringDhuhr
            )
            if (systemTime<prayTime.asr)
                context?.configureAdzanScheduler(
                prayTime.asr,
                AlarmHelper.mAsrRequestCode,
                PraytimeType.Asr,
                Prefs.ringAsr
            )
            if (systemTime<prayTime.magrib)
                context?.configureAdzanScheduler(
                prayTime.magrib,
                AlarmHelper.mMaghribRequestCode,
                PraytimeType.Maghrib,
                Prefs.ringMaghrib
            )
            if (systemTime<prayTime.isha)
                context?.configureAdzanScheduler(
                prayTime.isha,
                AlarmHelper.mIsyaRequestCode,
                PraytimeType.Isya,
                Prefs.ringIsya
            )
            if (systemTime<prayTime.nextFajr)
                context?.configureAdzanScheduler(
                prayTime.fajr,
                AlarmHelper.mNextFajrRequestCode,
                PraytimeType.Fajr,
                Prefs.ringFajr
            )


        }

        private fun Context.configureAdzanScheduler(
            triggerTime: Long,
            requestCode: Int,
            prayer: PraytimeType,
            ringType: Int
        ) {
            Log.i("123321", "configureAdzanScheduler: alerm set at ${triggerTime.toDateFormat()}")
            val alarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent =
                AdzanReceiver.getPendingIntent(this, requestCode, triggerTime, prayer, ringType)
            alarmMgr.cancel(alarmIntent)
            alarmMgr.setAlarmClock(AlarmManager.AlarmClockInfo(triggerTime, null), alarmIntent)
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