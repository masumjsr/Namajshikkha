package com.fsit.sohojnamaj.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsit.sohojnamaj.data.repository.NameRepository
import com.fsit.sohojnamaj.data.repository.SuraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(
    private val nameRepository: NameRepository
) : ViewModel(){
    val nameList=nameRepository.getName()
        .stateIn(
            scope =viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}