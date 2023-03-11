package com.fsit.sohojnamaj.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.fsit.sohojnamaj.model.PrayerTime
import com.fsit.sohojnamaj.model.Sura
import com.fsit.sohojnamaj.model.SuraDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface SuraDetailsDao {

    @Transaction
    @Query(
        value ="Select * from sura_details "
    )
    fun getSuraDetails(): Flow<List<SuraDetails>>
    @Upsert
    suspend fun updateSurah(sura: List<SuraDetails>)
}