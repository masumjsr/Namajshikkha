package com.fsit.sohojnamaj.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fsit.sohojnamaj.database.dao.*
import com.fsit.sohojnamaj.model.*
import com.fsit.sohojnamaj.model.dua.SubCategory
import com.fsit.sohojnamaj.model.dua.TypeOneItem
import com.fsit.sohojnamaj.model.dua.TypeTwoItem

@Database(
    entities = [
        Sura::class,
    SuraDetails::class,
    SubCategory::class,
    TypeOneItem::class,
    TypeTwoItem::class

    ],
    version =1,

)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getSuraDao(): SuraDao
    abstract fun getSuraDetailsDao():SuraDetailsDao
    abstract fun getSubCategoryDao():SubCategoryDao
    abstract fun getTypeOneDao():TypeOneItemDao
    abstract fun getTypeTwoDao():TypeTwoItemDao

}