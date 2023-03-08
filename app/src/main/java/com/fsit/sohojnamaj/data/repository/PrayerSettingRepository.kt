package com.fsit.sohojnamaj.data.repository

import com.fsit.sohojnamaj.datastore.PrayerPreferencesDataSource
import com.fsit.sohojnamaj.model.OffsetModel
import javax.inject.Inject

class PrayerSettingRepository @Inject constructor(
    private val  prayerPreferenceDataSource:PrayerPreferencesDataSource
) {
    val prayerPreferenceData=prayerPreferenceDataSource.prayerPreferenceData
    suspend fun updateOffset(id:Int,offSet:Int)=prayerPreferenceDataSource.updateOffset(id,offSet)
}