package com.fsit.sohojnamaj.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsit.sohojnamaj.data.repository.SubCategoryRepository
import com.fsit.sohojnamaj.data.repository.SuraRepository
import com.fsit.sohojnamaj.data.repository.TypeOneRepository
import com.fsit.sohojnamaj.data.repository.TypeTwoRepository
import com.fsit.sohojnamaj.ui.navigation.idArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TypeTwoViewModel @Inject constructor(
    private val typeTwoRepository: TypeTwoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    val id= savedStateHandle.get<Int>(idArg)?:-1
    val typeTwoList=typeTwoRepository.getTypeTwo(id)
        .stateIn(
            scope =viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}