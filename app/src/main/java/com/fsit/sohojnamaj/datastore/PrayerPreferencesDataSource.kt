package com.fsit.sohojnamaj.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import com.fsit.sohojnamaj.PrayerPreferences
import com.fsit.sohojnamaj.PrayerTime
import com.fsit.sohojnamaj.UserPreferences
import com.fsit.sohojnamaj.copy
import com.fsit.sohojnamaj.model.*
import com.google.android.gms.maps.model.LatLng
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
                    it.ishaOffset,

                ),
                LatLng(it.lat,it.lng),
                soundModel = SoundModel(
                    it.fajrAlerm,
                    it.dhuhrAlerm,
                    it.asrAlerm,
                    it.maghribAlerm,
                    it.ishaAlerm
                ),
                hijri = it.hijri,
                method = it.method,
                majhab = it.majhab,
                sound = it.sound,
                vibration = it.vibration,
                darkMode = it.darkmode
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
                    4->sunsetOffset=offSet
                    5->maghribOffset=offSet
                    6->ishaOffset=offSet
                }

            }



        }
    }
    suspend fun updateSound(id:Int,offSet:Int){
        prayerPreferences.updateData {
            Log.i("123321", "updateSound: id=$id, offSet=$offSet")

            it.copy {
                when(id){
                    0->fajrAlerm=offSet
                    1->dhuhrAlerm=offSet
                    2->asrAlerm=offSet
                    3->maghribAlerm=offSet
                    4->ishaAlerm=offSet
                }

            }

        }
    }
    suspend fun updateHijri(date:Int){
        prayerPreferences.updateData {
            it.copy { hijri=date }
        }
    }

    suspend fun updateMethod(methodValue:Int){
        prayerPreferences.updateData { it.copy { method=methodValue } }
    }
    suspend fun updateMajhab(majhabValue:Int){
        prayerPreferences.updateData { it.copy { majhab=majhabValue } }
    }

    suspend fun updateLocation(latLng: LatLng){
        prayerPreferences.updateData {
            it.copy {
                lat=latLng.latitude
                lng=latLng.longitude
            }
        }
    }
}