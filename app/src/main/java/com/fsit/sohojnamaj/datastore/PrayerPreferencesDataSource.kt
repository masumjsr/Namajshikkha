package com.fsit.sohojnamaj.datastore

import androidx.datastore.core.DataStore
import com.fsit.sohojnamaj.PrayerPreferences
import com.fsit.sohojnamaj.PrayerTime
import com.fsit.sohojnamaj.UserPreferences
import com.fsit.sohojnamaj.copy
import com.fsit.sohojnamaj.model.OffsetModel
import com.fsit.sohojnamaj.model.PrayerPreferenceModel
import com.fsit.sohojnamaj.model.Timings
import com.fsit.sohojnamaj.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrayerPreferencesDataSource @Inject constructor(
    private val prayerPreferences: DataStore<PrayerPreferences>
) {
    val prayerPreferenceData = prayerPreferences.data
        .map {
            PrayerPreferenceModel(
                OffsetModel(
                    it.fajrOffset,
                    it.sunriseOffset,
                    it.dhuhrOffset,
                    it.asrOffset,
                    it.maghribOffset,
                    it.sunsetOffset,
                    it.ishaOffset
                )
            )
        }
    suspend fun updateOffset(id:Int,offSet:Int){
        prayerPreferences.updateData {

            it.copy {
                when(id){
                    0->fajrOffset=offSet
                    1->sunriseOffset=offSet
                    2->dhuhrOffset=offSet
                    3->asrOffset=offSet
                    4->maghribOffset=offSet
                    5->ishaOffset=offSet
                }

            }

        }
    }
}