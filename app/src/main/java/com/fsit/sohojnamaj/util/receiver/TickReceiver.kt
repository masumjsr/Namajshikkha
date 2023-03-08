package com.fsit.sohojnamaj.util.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fsit.sohojnamaj.util.praytimes.Praytime

class TickReceiver:BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        Praytime.schedule(p0)
        //PraytimeWidget.update(p0)
    }
}