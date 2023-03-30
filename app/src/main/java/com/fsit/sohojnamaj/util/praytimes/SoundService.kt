package com.fsit.sohojnamaj.util.praytimes

import android.app.*
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.fsit.sohojnamaj.MainActivity
import com.fsit.sohojnamaj.R
import com.fsit.sohojnamaj.constants.Extra
import com.fsit.sohojnamaj.constants.Extra.ACTION_STOP_SERVICE
import com.fsit.sohojnamaj.data.Prefs


class SoundService : Service() {


    val ACTION_LAUNCH_SERVICE="launch"
    private var mp: MediaPlayer?=null
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("123321", "onStartCommand: start")

            if(intent?.action==ACTION_STOP_SERVICE){

                Log.i("123321", "onStartCommand: stop")
                mp?.stop()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    stopForeground(true)
                }
                else {
                    stopForeground(true)
                }
            }
        else if(intent?.action==ACTION_LAUNCH_SERVICE){
                Log.i("123321", "onStartCommand: launch")
                mp?.stop()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    stopForeground(true)

                }
                else {
                    stopForeground(true)
                }
            }

else {


                val stopSelf = Intent(this, SoundService::class.java)


                val resultIntent = Intent(this, MainActivity::class.java)
                resultIntent.putExtra("stop",true)
// Create the TaskStackBuilder
                val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
                    // Add the intent, which inflates the back stack
                    addNextIntentWithParentStack(resultIntent)
                    // Get the PendingIntent containing the entire back stack
                    getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                }

                stopSelf.action = ACTION_STOP_SERVICE
                val pStopSelf =
                    PendingIntent.getService(this, 0, stopSelf, FLAG_IMMUTABLE)
                if(!intent?.getStringExtra(Extra.EXTRA_PRAYER).isNullOrEmpty()) {
                    val notification = NotificationCompat.Builder(applicationContext, mChannelId)
                        .setContentTitle("নামাজের সময়")
                        .setContentText("${intent?.getStringExtra(Extra.EXTRA_PRAYER)} এর ওয়াক্ত")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setContentIntent(resultPendingIntent)
                        .setDeleteIntent(pStopSelf)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .addAction(R.drawable.baseline_close_24,"Close",pStopSelf)
                        .build()
                    startForeground(1, notification)}

        else
        {
            stopSelf()
        }
    }
                return START_REDELIVER_INTENT

    }

    override fun onCreate() {

        mp=MediaPlayer()

        mp?.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
        )
        mp?.setDataSource(applicationContext, Uri.parse("android.resource://"+applicationContext.packageName+"/"+Prefs.muadzin))
        mp?.prepare()
        mp?.start()
        mp?.setOnCompletionListener {
            stopForeground(false)
            val service =Intent(applicationContext, SoundService::class.java)
                stopService(service)
        }
    }


    val mChannelId = "com.fsit.sohojnamaj.adzan"
    private var notificationManager: NotificationManager? = null
    val mChannelNameAdzan = "Adzan"

    private fun Context.createNotification(text: String) {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager


        val notification = NotificationCompat.Builder(applicationContext, mChannelId)
            .setContentTitle("Prayer Time")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)

            .setSound(Uri.parse("android.resource://" + applicationContext.packageName + "/" + Prefs.muadzin))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        notificationManager?.notify(0, notification)
    }

    override fun onDestroy() {

        Log.i("123321", "onDestroy: ")

        mp?.stop()
    }

}