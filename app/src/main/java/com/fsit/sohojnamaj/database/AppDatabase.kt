package com.fsit.sohojnamaj.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fsit.sohojnamaj.database.dao.AyatArDao
import com.fsit.sohojnamaj.database.dao.AyatBnDao
import com.fsit.sohojnamaj.database.dao.PrayerDao
import com.fsit.sohojnamaj.database.dao.SuraDao
import com.fsit.sohojnamaj.model.AyatAr
import com.fsit.sohojnamaj.model.AyatBn
import com.fsit.sohojnamaj.model.PrayerTime
import com.fsit.sohojnamaj.model.Sura

@Database(
    entities = [
        Sura::class,
    AyatAr::class,
    AyatBn::class

    ],
    version =1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getSuraDao(): SuraDao
    abstract fun getAyatArDao():AyatArDao
    abstract fun getAyatBnDao():AyatBnDao

}