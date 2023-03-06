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

class NetworkRepository @Inject constructor(
    private  val prayerDao: PrayerDao,
    private val networkDataSource: NetworkDataSource
) :Syncable{
    override suspend fun syncWith(synchronizer: Synchronizer)=
        synchronizer.changeListSync(
            modelDeleter = {},
            modelUpdater = {
                val  calender = Calendar.getInstance()
                val networkResponse = networkDataSource.getPrayerTime(calender.get(Calendar.MONTH)+1)
                val list =ArrayList<PrayerTime>()
                networkResponse.data.forEach {
                    list.add(
                        PrayerTime(
                            SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date(it.date.timestamp.toLong()*1000)),
                            it.timings.Asr,
                            it.timings.Dhuhr,
                            it.timings.Fajr,
                            it.timings.Firstthird,
                            it.timings.Imsak,
                            it.timings.Isha,
                            it.timings.Lastthird,
                            it.timings.Maghrib,
                            it.timings.Midnight,
                            it.timings.Sunrise,
                            it.timings.Sunrise)

                    )
                }
                prayerDao.updatePrayerTime(list)
                calender.add(Calendar.MONTH,1)
                val networkResponse2 = networkDataSource.getPrayerTime(calender.get(Calendar.MONTH)+1)
                networkResponse2.data.forEach {
                    prayerDao.updatePrayerTime(
                        PrayerTime(
                            SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date(it.date.timestamp.toLong()*1000)),
                            it.timings.Asr,
                            it.timings.Dhuhr,
                            it.timings.Fajr,
                            it.timings.Firstthird,
                            it.timings.Imsak,
                            it.timings.Isha,
                            it.timings.Lastthird,
                            it.timings.Maghrib,
                            it.timings.Midnight,
                            it.timings.Sunrise,
                            it.timings.Sunrise)
                    )
                }
            }
        )

}