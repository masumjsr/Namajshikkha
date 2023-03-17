package com.fsit.sohojnamaj.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsit.sohojnamaj.data.repository.SubCategoryRepository
import com.fsit.sohojnamaj.data.repository.SuraRepository
import com.fsit.sohojnamaj.data.repository.TypeOneRepository
import com.fsit.sohojnamaj.ui.navigation.idArg
import com.fsit.sohojnamaj.ui.navigation.titleArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TypeOneViewModel @Inject constructor(
    private val typeOneRepository: TypeOneRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    val id= savedStateHandle.get<Int>(idArg)?:-1
    val title= savedStateHandle.get<String>(titleArg)?:""

    val typeOneList=typeOneRepository.getTypeOne(id)
        .stateIn(
            scope =viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}