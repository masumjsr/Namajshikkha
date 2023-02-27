package com.fsit.sohojnamaj.util.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fsit.sohojnamaj.util.praytimes.PrayTime


class AutoStartReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        PrayTime.schedule(context)
    }
}
