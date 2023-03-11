package com.fsit.sohojnamaj.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsit.sohojnamaj.data.repository.SuraDetailsRepository
import com.fsit.sohojnamaj.model.SuraDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SuraDetailsViewModel @Inject constructor(
    private val suraDetailsRepository: SuraDetailsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    val id = savedStateHandle.get<Int>("id")?:-1
    val title = savedStateHandle.get<String>("title")

    val suraDetails:StateFlow<List<SuraDetails>> =
        suraDetailsRepository.getSuraDetails(id)
        .stateIn(
            scope =viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}