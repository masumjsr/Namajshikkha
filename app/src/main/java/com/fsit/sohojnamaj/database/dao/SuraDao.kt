package com.fsit.sohojnamaj.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.fsit.sohojnamaj.model.PrayerTime
import com.fsit.sohojnamaj.model.Sura
import kotlinx.coroutines.flow.Flow

@Dao
interface SuraDao {

    @Transaction
    @Query(
        value ="Select * from sura "
    )
    fun getSura(): Flow<List<Sura>>
    @Upsert
    suspend fun updateSurah(sura: List<Sura>)
}