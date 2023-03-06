package com.fsit.sohojnamaj.database.dao

import com.fsit.sohojnamaj.model.PrayerTime
import java.text.SimpleDateFormat
import java.util.*

class FakePrayerDao {
    val prayerList = arrayListOf(


        PrayerTime(
            date="20230303",
            Asr="16:28 (+06)",
            Dhuhr="12:15 (+06)",
            Fajr="05:09 (+06)",
            Firstthird="22:13 (+06)",
            Imsak="04:59 (+06)",
            Isha="19:22 (+06)",
            Lastthird="02:18 (+06)",
            Maghrib="18:07 (+06)",
            Midnight="00:15 (+06)",
            Sunrise="06:23 (+06)",
            Sunset="06:23 (+06)"),
        PrayerTime(
            date="20230304",
            Asr="2023-03-01T16:28:00+06:00 (+06)",
            Dhuhr= "2023-03-01T12:16:00+06:00 (+06)",
            Fajr="2023-03-01T05:10:00+06:00 (+06)",
            Firstthird="22:13 (+06)",
            Imsak="04:58 (+06)",
            Isha="2023-03-01T19:21:00+06:00 (+06)",
            Lastthird="02:18 (+06)",
            Maghrib="2023-03-01T18:06:00+06:00 (+06)",
            Midnight="00:15 (+06)",
            Sunrise= "2023-03-01T06:25:00+06:00 (+06)",
            Sunset="06:23 (+06)"),
        PrayerTime(
            date="20230305",
            Asr= "2023-03-05T16:29:00+06:00 (+06)",
            Dhuhr="2023-03-05T12:15:00+06:00 (+06)",
            Fajr="2023-03-05T05:07:00+06:00 (+06)",
            Firstthird="22:13 (+06)",
            Imsak="04:57 (+06)",
            Isha="2023-03-05T19:23:00+06:00 (+06)",
            Lastthird="02:17 (+06)",
            Maghrib="2023-03-05T18:08:00+06:00 (+06)",
            Midnight="00:15 (+06)",
            Sunrise="2023-03-05T06:22:00+06:00 (+06)",
            Sunset="06:22 (+06)"
        ),
        PrayerTime(
            date="20230306",
            Asr= "2023-03-06T16:30:00+06:00 (+06)",
            Dhuhr="2023-03-06T12:14:00+06:00 (+06)",
            Fajr="2023-03-06T05:06:00+06:00 (+06)",
            Firstthird="22:13 (+06)",
            Imsak="04:57 (+06)",
            Isha="2023-03-06T19:23:00+06:00 (+06)",
            Lastthird="02:17 (+06)",
            Maghrib="2023-03-06T18:08:00+06:00 (+06)",
            Midnight="00:15 (+06)",
            Sunrise="2023-03-06T06:21:00+06:00 (+06)",
            Sunset="06:22 (+06)"
        )
    )
    fun getPrayer(date:String):PrayerTime {
        println("getPrayer:$date")
        val todayPrayer = prayerList.filter { it.date == date }.first()
        return todayPrayer
    }




}