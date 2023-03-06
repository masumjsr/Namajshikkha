package com.fsit.sohojnamaj.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fsit.sohojnamaj.database.dao.PrayerDao
import com.fsit.sohojnamaj.model.PrayerTime

@Database(
    entities = [
        PrayerTime::class
    ],
    version =1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getPrayerDao(): PrayerDao

}