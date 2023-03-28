package com.fsit.sohojnamaj.ui.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsit.sohojnamaj.data.repository.SubCategoryRepository
import com.fsit.sohojnamaj.data.repository.SuraRepository
import com.fsit.sohojnamaj.ui.navigation.idArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SubCategoryViewModel @Inject constructor(
    private val subCategoryRepository: SubCategoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    val id= savedStateHandle.get<Int>(idArg)?:-1
    val title= listOf("","গোসল ও ওযু","পাঁচ কালেমা সমূহ","নামাজের দোয়া","নামাজের নিয়ত","জুম্মার নামাজ","রোযা ও তারাবী","দুই ইদ","যাকাত","হজ ও ওমরা ","কুরবানী ও আকিকা","হাদিস","জুম্মার নামাজ","রোযা ও তারাবী","দুই ইদ")[id]

    val subcategoryList=subCategoryRepository.getSubCategory(id)
        .stateIn(
            scope =viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}