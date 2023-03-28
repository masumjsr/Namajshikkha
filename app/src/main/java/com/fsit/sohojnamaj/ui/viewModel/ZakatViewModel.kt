package com.fsit.sohojnamaj.ui.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsit.sohojnamaj.model.ZakatModel
import com.fsit.sohojnamaj.model.modelList
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class ZakatViewModel : ViewModel(){
    var _infoList=  mutableStateOf(modelList)
    var infoList= MutableStateFlow(_infoList.value)
    var goldPrice=MutableStateFlow("10000")
    var totalPrice=MutableStateFlow(0.0)
    val  imageList=   mutableStateMapOf<Int, Double>()


    val mutableList: MutableList<Double> = mutableListOf(0.0,0.0,0.0,0.0)
    val flowList= flowOf(mutableList)
    init {

    }

    fun update(model:ZakatModel){
        try {
            imageList[model.id] = model.amount.toDouble()
        } catch (e: Exception) {
            imageList[model.id] = 0.0

        }

    }
    fun updateGoldPrice(price:String){
        goldPrice= MutableStateFlow(price)
    }



}