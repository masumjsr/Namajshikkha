package com.fsit.sohojnamaj.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.fsit.sohojnamaj.model.AyatAr
import com.fsit.sohojnamaj.model.AyatBn
import com.fsit.sohojnamaj.model.PrayerTime
import com.fsit.sohojnamaj.model.Sura
import kotlinx.coroutines.flow.Flow

@Dao
interface AyatBnDao {

    @Transaction
    @Query(
        value ="Select * from ayat_bn  where sura = :id"
    )
    fun getAyatBn(id:Int): Flow<List<AyatBn>>
}