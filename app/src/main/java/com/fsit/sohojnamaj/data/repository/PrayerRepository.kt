package com.fsit.sohojnamaj.data.repository

import android.util.Log
import com.fsit.sohojnamaj.database.dao.PrayerDao
import com.fsit.sohojnamaj.datastore.PreferencesDataSource
import com.fsit.sohojnamaj.model.PrayerTime
import com.fsit.sohojnamaj.network.NetworkDataSource
import com.fsit.sohojnamaj.util.sync.Syncable
import com.fsit.sohojnamaj.util.sync.Synchronizer
import com.fsit.sohojnamaj.util.sync.changeListSync
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PrayerRepository @Inject constructor(
    private  val prayerDao: PrayerDao,
) {
    val prayerData =  prayerDao.getPrayerTimes()
    fun prayer(date:String)=prayerDao.getPrayer(date)

}