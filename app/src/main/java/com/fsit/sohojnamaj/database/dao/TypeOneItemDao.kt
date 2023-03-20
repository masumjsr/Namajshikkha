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
import kotlinx.coroutines.flow.Flow

@Dao
interface TypeOneItemDao {

    @Transaction
    @Query(
        value ="Select * from type_one where sub_category=:sub_category"
    )
    fun getTypeOne(sub_category:Int): Flow<List<TypeOneItem>>

    @Upsert
    fun update(list:List<TypeOneItem>)
}