package com.fsit.sohojnamaj.sync.initializer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import com.fsit.sohojnamaj.R

private const val SyncNotificationId = 0
private const val SyncNotificationChannelID  = "SyncNotificationChannel"

val SyncConstraints
get() = Constraints.Builder()
    .setRequiredNetworkType(NetworkType.CONNECTED)
    .build()

fun Context.syncForegroundInfo() = ForegroundInfo(
    SyncNotificationId,
    syncWorkNotification()
)

private  fun Context.syncWorkNotification():Notification{
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            SyncNotificationChannelID,
            getString(R.string.sync_notification_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description =getString(R.string.sync_notification_channel_description)
        }

        val notificationManager: NotificationManager? =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        notificationManager?.createNotificationChannel(channel)
    }
    return NotificationCompat.Builder(
        this,
        SyncNotificationChannelID
    )
        .setSmallIcon(R.drawable.ic_android_black_24dp)
        .setContentTitle(getString(R.string.sync_notification_title))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()
}