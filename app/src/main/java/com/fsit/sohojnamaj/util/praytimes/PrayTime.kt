package com.fsit.sohojnamaj.util.praytimes

import android.app.AlarmManager
import android.content.Context
import com.fsit.sohojnamaj.util.AlarmHelper
import com.fsit.sohojnamaj.util.receiver.AdzanReceiver

class PrayTime{
    companion object {
        fun schedule(context: Context?) {
            context?.configureAdzanScheduler(
                System.currentTimeMillis() + (20 * 1000),
                AlarmHelper.mFajrRequestCode,
                PraytimeType.Fajr,
                2

            )
        }

        private fun Context.configureAdzanScheduler(
            triggerTime: Long,
            requestCode: Int,
            prayer: PraytimeType,
            ringType: Int
        ) {
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