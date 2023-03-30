package com.fsit.sohojnamaj.ui.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsit.sohojnamaj.data.repository.LocalRepository
import com.fsit.sohojnamaj.data.repository.NetworkRepository
import com.fsit.sohojnamaj.data.repository.PrayerRepository
import com.fsit.sohojnamaj.data.repository.PrayerSettingRepository
import com.fsit.sohojnamaj.model.OffsetModel
import com.fsit.sohojnamaj.model.PrayerPreferenceModel
import com.fsit.sohojnamaj.model.SoundModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val  prayerRepository: PrayerSettingRepository,
) : ViewModel(){
    val settings:StateFlow<PrayerPreferenceModel> = prayerRepository
        .prayerPreferenceData
        .map {
            it
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PrayerPreferenceModel(OffsetModel(), LatLng(0.0,0.0), SoundModel())
        )
    fun updateOffset(id:Int,offSet:Int){

        viewModelScope.launch {
            prayerRepository.updateOffset(id,offSet)
        }
    }
    fun updateSound(id:Int,offSet:Int){

        viewModelScope.launch {
            prayerRepository.updateSound(id,offSet)
        }
    }
    fun updateHijri(value:Int){
        viewModelScope.launch { prayerRepository.updatehijri(value) }
    }
    fun updateTheme(value:Int){
        viewModelScope.launch { prayerRepository.updateTheme(value) }
    }

    fun updateMethod(value:Int){
        viewModelScope.launch { prayerRepository.updatemethod(value) }
    }
    fun updateMajhab(value:Int){
        viewModelScope.launch { prayerRepository.updateMajhab(value) }
    }


}