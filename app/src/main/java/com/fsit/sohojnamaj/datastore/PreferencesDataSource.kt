package com.fsit.sohojnamaj.datastore

import androidx.datastore.core.DataStore
import com.fsit.sohojnamaj.UserPreferences
import com.fsit.sohojnamaj.copy
import com.fsit.sohojnamaj.model.Timings
import com.fsit.sohojnamaj.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userdata = userPreferences.data
        .map {
            UserData(
                fajr = it.fajr,
                sunrise = it.sunrise,
                dhur = it.dhuhr,
                asr = it.asr,
                maghrib = it.maghrib,
                isha = it.isha,
                midnight = it.midnight,
                firstThird = it.firstthird,
                lastThird = it.lastthird
            )
        }
    suspend fun updateUserData(userData: Timings){
        userPreferences.updateData {
            it.copy {
                fajr = userData.Fajr
                sunrise = userData.Sunrise
                dhuhr = userData.Dhuhr
                asr = userData.Asr
                maghrib = userData.Maghrib
                isha = userData.Isha
                midnight = userData.Midnight
                firstthird = userData.Firstthird
                lastthird = userData.Lastthird
            }

        }
    }
}