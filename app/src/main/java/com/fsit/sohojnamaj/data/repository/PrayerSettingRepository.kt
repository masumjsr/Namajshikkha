package com.fsit.sohojnamaj.data.repository

import com.fsit.sohojnamaj.datastore.PrayerPreferencesDataSource
import com.fsit.sohojnamaj.model.OffsetModel
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class PrayerSettingRepository @Inject constructor(
    private val  prayerPreferenceDataSource:PrayerPreferencesDataSource
) {
    val prayerPreferenceData=prayerPreferenceDataSource.prayerPreferenceData
    suspend fun updateLocation(latLng: LatLng)=prayerPreferenceDataSource.updateLocation(latLng)
    suspend fun updateOffset(id:Int,offSet:Int)=prayerPreferenceDataSource.updateOffset(id,offSet)
    suspend fun updateSound(id:Int,offSet:Int)=prayerPreferenceDataSource.updateSound(id,offSet)
    suspend fun updatehijri(i:Int)=prayerPreferenceDataSource.updateHijri(i)
    suspend fun updateTheme(i:Int)=prayerPreferenceDataSource.updateTheme(i)
    suspend fun updatemethod(id:Int)=prayerPreferenceDataSource.updateMethod(id)
    suspend fun updateMajhab(id:Int)=prayerPreferenceDataSource.updateMajhab(id)
}