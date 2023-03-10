package com.fsit.sohojnamaj.util.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.fsit.sohojnamaj.PrayerTime
import com.fsit.sohojnamaj.R
import com.fsit.sohojnamaj.constants.RingType
import com.fsit.sohojnamaj.data.repository.LocalRepository
import com.fsit.sohojnamaj.data.repository.PrayerRepository
import com.fsit.sohojnamaj.data.repository.PrayerSettingRepository
import com.fsit.sohojnamaj.model.AlarmModel
import com.fsit.sohojnamaj.util.dateUtil.*
import com.fsit.sohojnamaj.util.foreground.AdzanForegroundNotification
import com.fsit.sohojnamaj.util.praytimes.Praytime
import com.fsit.sohojnamaj.util.praytimes.PraytimeType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AdzanReceiver : BroadcastReceiver() {
    @Inject
    lateinit var localRepository:LocalRepository
    @Inject
    lateinit var settingRepository: PrayerSettingRepository

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
                    PendingIntent.FLAG_IMMUTABLE
                )
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("123321", "onReceive: adazan received")
        notificationManager  = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val triggerTime = intent?.getLongExtra(EXTRA_TRIGGER_TIME,0)?:0
        val prayer = PraytimeType.valueOf(intent?.getStringExtra(EXTRA_PRAYER) ?: PraytimeType.Refresh.name)
        val ringType = intent?.getIntExtra(EXTRA_RING_TYPE, RingType.SOUND)

        if(Calendar.getInstance().timeInMillis in triggerTime-2000.. triggerTime+2000){
            if(ringType == RingType.SOUND){
                WorkManager.getInstance(context)
                    .enqueue(OneTimeWorkRequestBuilder<AdzanForegroundNotification>()
                        .setInputData(workDataOf(EXTRA_PRAYER to prayer.name))
                        .build()
                    )
            } else if(ringType== RingType.NOTIFICATION){
               context.createNotification(prayer.name)
            }
        }

      CoroutineScope(Dispatchers.IO).launch {
          combine(localRepository.userData,settingRepository.prayerPreferenceData){ setting, prayer->


            Praytime.schedule(context,prayer,setting)



         }.collect()
      }

    }


    val mChannelNameAdzan = "Adzan"
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(mChannelId, mChannelNameAdzan, importance)
        notificationManager?.createNotificationChannel(mChannel)
    }

    private fun Context.createNotification(text: String) {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val notification = NotificationCompat.Builder(applicationContext, mChannelId)
            .setContentTitle("Prayer Time")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        notificationManager?.notify(0, notification)
    }

}