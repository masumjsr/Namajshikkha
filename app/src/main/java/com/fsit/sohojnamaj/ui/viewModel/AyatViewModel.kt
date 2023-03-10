package com.fsit.sohojnamaj.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsit.sohojnamaj.data.repository.AyatRepository
import com.fsit.sohojnamaj.data.repository.SuraRepository
import com.fsit.sohojnamaj.model.Ayat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AyatViewModel @Inject constructor(
    private val ayatRepository: AyatRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    val id = savedStateHandle.get<Int>("id")?:-1
    val title = savedStateHandle.get<String>("title")
    val banglaAyat=ayatRepository.getBangaAyat(id)
    val arabicAyat = ayatRepository.getArabicAyat(id)

    val ayatList:StateFlow<List<Ayat>> =arabicAyat
        .combine(banglaAyat){arabic,bangla->
            arabic.map {ayatAr->
                Ayat(ayatAr,bangla.find {  it.id==ayatAr.id}!!)
            }

        }
        .stateIn(
            scope =viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}