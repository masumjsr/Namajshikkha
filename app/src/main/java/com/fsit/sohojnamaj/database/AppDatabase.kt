package com.fsit.sohojnamaj.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fsit.sohojnamaj.database.dao.*
import com.fsit.sohojnamaj.model.*

@Database(
    entities = [
        Sura::class,
    SuraDetails::class

    ],
    version =1,

)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getSuraDao(): SuraDao
    abstract fun getSuraDetailsDao():SuraDetailsDao

}