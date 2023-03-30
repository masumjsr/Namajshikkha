package com.fsit.sohojnamaj.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.fsit.sohojnamaj.PrayerTime
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
                previousIsha = it.previousIsha,
                nextFajr = it.nextFajr,
                nextMagrib = it.nextMaghrib,
                location = it.location

            )
        }
    suspend fun updateUserData(userData: UserData){

        userPreferences.updateData {
            it.copy {
                fajr = userData.fajr?:""
                sunrise = userData.sunrise?:""
                dhuhr = userData.dhur?:""
                asr = userData.asr?:""
                maghrib = userData.maghrib?:""
                isha = userData.isha?:""
                previousIsha= userData.previousIsha
                nextFajr=userData.nextFajr
                nextMaghrib=userData.nextMagrib
                location = userData.location

            }

        }
    }
}