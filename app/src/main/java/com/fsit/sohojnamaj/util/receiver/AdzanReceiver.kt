package com.fsit.sohojnamaj.util.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.fsit.sohojnamaj.constants.RingType
import com.fsit.sohojnamaj.util.foreground.AdzanForegroundNotification
import com.fsit.sohojnamaj.util.praytimes.PrayTime
import com.fsit.sohojnamaj.util.praytimes.PraytimeType
import java.util.*

class AdzanReceiver : BroadcastReceiver() {

    val mChannelId = "com.fsit.sohojnamaj.adzan"
    private var notificationManager: NotificationManager? = null

    companion object {
        const val EXTRA_PRAYER = "extra_prayer"
        const val EXTRA_RING_TYPE = "extra_ring"
        const val EXTRA_TRIGGER_TIME = "extra_trigger_time"

        fun getPendingIntent(
            context: Context,
            requestCode: Int,
            triggerTime: Long,
            praytimeType: PraytimeType,
            ringType: Int
        ): PendingIntent {
            return Intent(context,AdzanReceiver::class.java).let { intent ->
                intent.putExtra(EXTRA_PRAYER,praytimeType.name)
                intent.putExtra(EXTRA_TRIGGER_TIME,triggerTime)
                intent.putExtra(EXTRA_RING_TYPE,ringType)

                PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        notificationManager  = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val triggerTime = intent?.getLongExtra(EXTRA_TRIGGER_TIME,0)?:0
        val prayer = PraytimeType.valueOf(intent?.getStringExtra(EXTRA_PRAYER) ?: PraytimeType.Refresh.name)
        val ringType = intent?.getIntExtra(EXTRA_RING_TYPE, RingType.SILENT)

        if(Calendar.getInstance().timeInMillis in triggerTime-2000.. triggerTime+2000){
            if(ringType == RingType.SOUND){
                WorkManager.getInstance(context)
                    .enqueue(OneTimeWorkRequestBuilder<AdzanForegroundNotification>()
                        .setInputData(workDataOf(EXTRA_PRAYER to prayer.name))
                        .build()
                    )
            } else if(ringType== RingType.NOTIFICATION){
                Log.i("123321", "onReceive: ring type is notification")
            }
        }

        PrayTime.schedule(context)

    }

}