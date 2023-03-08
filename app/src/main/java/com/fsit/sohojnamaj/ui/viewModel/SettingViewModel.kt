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
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val  prayerRepository: PrayerSettingRepository,
) : ViewModel(){
    val offset:StateFlow<OffsetModel> = prayerRepository
        .prayerPreferenceData
        .map {
            it.offsetModel
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue =OffsetModel()
        )
    fun updateOffset(id:Int,offSet:Int){

        Log.i("123321", "updateOffset: updateOffset $offset")
        viewModelScope.launch {
            prayerRepository.updateOffset(id,offSet)
        }
    }
    init {
        viewModelScope.launch {
            offset.collectLatest {
                Log.i("123321", "viewmodel offset response:${it} ")
            }
        }
    }

}