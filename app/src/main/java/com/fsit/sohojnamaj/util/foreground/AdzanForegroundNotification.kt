package com.fsit.sohojnamaj.util.foreground

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.RxWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.fsit.sohojnamaj.R
import com.fsit.sohojnamaj.constants.NotificationConstants
import com.fsit.sohojnamaj.data.Prefs
import com.wagyufari.dzikirqu.constants.Extra
import io.reactivex.Single

class AdzanForegroundNotification(appContext: Context, workerParams: WorkerParameters):
    RxWorker(appContext,workerParams){
    val mChannelId = "com.fsit.sohojnamaj.adzan"
    private var notificationManager: NotificationManager? =null

    val mp = MediaPlayer()

    override fun createWork(): Single<Result> {
        applicationContext.createForegroundNotification("${inputData.getString(
            Extra.EXTRA_PRAYER).toString()} waqt salat")

        return Single.create { emitter->
            mp.setAudioStreamType(AudioManager.STREAM_RING)
            mp.setDataSource(applicationContext, Uri.parse("android.resource://"+applicationContext.packageName+"/"+Prefs.muadzin))
            mp.prepare()
            mp.start()
            mp.setOnCompletionListener {
                emitter.onSuccess(Result.success())
                applicationContext.createNotification("Fazor waqt ")
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

        val notification = NotificationCompat.Builder(applicationContext,mChannelId)
            .setContentText(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
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