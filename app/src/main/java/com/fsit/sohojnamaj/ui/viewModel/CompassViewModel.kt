package com.fsit.sohojnamaj.ui.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsit.sohojnamaj.data.repository.LocalRepository
import com.fsit.sohojnamaj.data.repository.PrayerSettingRepository
import com.fsit.sohojnamaj.model.*
import com.fsit.sohojnamaj.util.dateUtil.*
import com.fsit.sohojnamaj.util.praytimes.PrayerTimeHelper
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class CompassViewModel @Inject constructor(
    private val prayerSettingRepository: PrayerSettingRepository
) : ViewModel(){



    val locationData:StateFlow<LatLng> =prayerSettingRepository.prayerPreferenceData

        .map {
            it.latLng

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LatLng(0.0,0.0)
        )


    var isFacingQibla by mutableStateOf(false)
    var qilbaRotation by mutableStateOf(RotationTarget(0f, 0f))
    var compassRotation by mutableStateOf(RotationTarget(0f, 0f))

    var locationAddress by mutableStateOf("-")

    fun updateCompass(qilba: RotationTarget, compass: RotationTarget, isFacing: Boolean) {
        isFacingQibla = isFacing
        qilbaRotation = qilba
        compassRotation = compass
    }




}