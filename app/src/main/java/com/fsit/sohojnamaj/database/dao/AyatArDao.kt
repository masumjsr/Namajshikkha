package com.fsit.sohojnamaj.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.fsit.sohojnamaj.model.AyatAr
import com.fsit.sohojnamaj.model.PrayerTime
import com.fsit.sohojnamaj.model.Sura
import kotlinx.coroutines.flow.Flow

@Dao
interface AyatArDao {

    @Transaction
    @Query(
        value ="Select * from ayat_ar where sura = :id"
    )
    fun getAyatAr(id:Int): Flow<List<AyatAr>>
}