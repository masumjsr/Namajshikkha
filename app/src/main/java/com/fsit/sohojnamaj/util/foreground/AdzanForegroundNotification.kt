package com.fsit.sohojnamaj.util.foreground

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.RxWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.fsit.sohojnamaj.MainActivity
import com.fsit.sohojnamaj.R
import com.fsit.sohojnamaj.constants.Extra
import com.fsit.sohojnamaj.constants.NotificationConstants
import com.fsit.sohojnamaj.data.Prefs
import com.fsit.sohojnamaj.util.praytimes.SoundService
import io.reactivex.Single


class AdzanForegroundNotification(appContext: Context, workerParams: WorkerParameters):
    RxWorker(appContext,workerParams){
    val mChannelId = "com.fsit.sohojnamaj.adzan"
    private var notificationManager: NotificationManager? =null

    private val mp: MediaPlayer = MediaPlayer()

    override fun createWork(): Single<Result> {

        val triggerTime= inputData.getLong(Extra.EXTRA_TRIGGER_TIME,0)



        return Single.create { emitter->




            val service =Intent(applicationContext, SoundService::class.java)
            service.putExtra(Extra.EXTRA_PRAYER,"")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                applicationContext.startForegroundService(service)
            }
            else{
                applicationContext.startService(service)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(){

        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(mChannelId, NotificationConstants.mChannelNameAdzan,importance)
        notificationManager?.createNotificationChannel(mChannel)
    }

    private fun Context.createForegroundNotification(text: String){
        notificationManager =  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val title = "Prayer Time"

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            createChannel()
        }

        WorkManager.getInstance(applicationContext)
        val notificationIntent = Intent(this.applicationContext, MainActivity::class.java)
        val contentIntent =
            PendingIntent.getActivity(this.applicationContext, 0, notificationIntent, 0)


        val notification = NotificationCompat.Builder(applicationContext,mChannelId)
            .setContentText(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setContentIntent(contentIntent)
            .setDeleteIntent(WorkManager.getInstance(applicationContext).createCancelPendingIntent(id))
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .build()

        notificationManager?.notify(0,notification)


    }
    private fun Context.createNotification(text: String){
        notificationManager =  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val title  =text
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            createChannel()
        }

        val notification = NotificationCompat.Builder(applicationContext,mChannelId)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .build()


        notificationManager?.notify(0,notification)

    }

}