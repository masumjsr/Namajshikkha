package com.fsit.sohojnamaj.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.fsit.sohojnamaj.model.PrayerTime
import com.fsit.sohojnamaj.model.Sura
import com.fsit.sohojnamaj.model.SuraDetails
import com.fsit.sohojnamaj.model.dua.SubCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface SubCategoryDao {

    @Transaction
    @Query(
        value ="Select * from sub_category where category=:category"
    )
    fun getSubCategory(category:Int): Flow<List<SubCategory>>
}