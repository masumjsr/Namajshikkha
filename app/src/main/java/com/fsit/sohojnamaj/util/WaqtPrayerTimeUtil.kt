package com.fsit.sohojnamaj.util

import androidx.compose.runtime.collectAsState
import com.fsit.sohojnamaj.database.dao.FakePrayerDao
import com.fsit.sohojnamaj.model.PrayerTime
import com.fsit.sohojnamaj.util.dateUtil.*
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject

class WaqtPrayerTimeUtil @Inject constructor(
    private  val prayerDao: FakePrayerDao

){
    val calender: Calendar = Calendar.getInstance()
    val todayPrayer =  prayerDao.getPrayer(today(calender))

    val tomorrowPrayer =  prayerDao.getPrayer(tomorrow(calender))

    val fajr = todayPrayer.Fajr.toISO8601Date()
    val sunrise = todayPrayer.Sunrise.toISO8601Date()
    val Dhuhr = todayPrayer.Dhuhr.toISO8601Date()
    val Asr = todayPrayer.Asr.toISO8601Date()
    val Maghrib = todayPrayer.Maghrib.toISO8601Date()
    val Isha = todayPrayer.Isha.toISO8601Date()
    val nextFajr = tomorrowPrayer.Fajr.toISO8601Date()
    val nextSunrise = tomorrowPrayer.Sunrise.toISO8601Date()




}