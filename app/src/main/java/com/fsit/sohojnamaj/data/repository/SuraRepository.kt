package com.fsit.sohojnamaj.data.repository

import com.fsit.sohojnamaj.database.dao.SuraDao
import com.fsit.sohojnamaj.datastore.PreferencesDataSource
import com.fsit.sohojnamaj.model.UserData
import javax.inject.Inject

class SuraRepository @Inject constructor(
    val sura: SuraDao
){
    fun getSura()=sura.getSura()
}