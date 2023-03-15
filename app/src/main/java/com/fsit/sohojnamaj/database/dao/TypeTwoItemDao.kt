package com.fsit.sohojnamaj.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.fsit.sohojnamaj.model.PrayerTime
import com.fsit.sohojnamaj.model.Sura
import com.fsit.sohojnamaj.model.SuraDetails
import com.fsit.sohojnamaj.model.dua.SubCategory
import com.fsit.sohojnamaj.model.dua.TypeOneItem
import com.fsit.sohojnamaj.model.dua.TypeTwoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TypeTwoItemDao {

    @Transaction
    @Query(
        value ="Select * from type_two where sub_category=:sub_category"
    )
    fun getTypeTwo(sub_category:Int): Flow<List<TypeTwoItem>>
}