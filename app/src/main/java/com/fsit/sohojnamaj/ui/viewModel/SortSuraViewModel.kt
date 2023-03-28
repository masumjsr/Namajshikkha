package com.fsit.sohojnamaj.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsit.sohojnamaj.data.repository.SuraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SortSuraViewModel @Inject constructor(
    private val suraRepository: SuraRepository
) : ViewModel(){
    val suraNo= flowOf(listOf(1,105,106, 107,108,109,110,111,112,113,114))

    val suraList=suraRepository.getSura()

        .combine(suraNo){a,v->

            a.filter {
                v.contains(it.sura_no)
            }
        }
        .stateIn(
            scope =viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}