package com.fsit.sohojnamaj.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.fsit.sohojnamaj.model.PrayerTime
import kotlinx.coroutines.flow.Flow

@Dao
interface PrayerDao {

    @Transaction
    @Query(
        value ="Select * from prayerTime "
    )
    fun getPrayerTimes(): Flow<List<PrayerTime>>
    @Query("Select * from prayerTime where date=:date")
    fun getPrayer(date: String): Flow<PrayerTime?>
    @Upsert
    suspend fun updatePrayerTime(prayerTime: List<PrayerTime>)
    @Upsert
    suspend fun updatePrayerTime(prayerTime: PrayerTime)
}