package com.fsit.sohojnamaj.data.repository

import com.fsit.sohojnamaj.database.dao.NameDao
import com.fsit.sohojnamaj.database.dao.SubCategoryDao
import com.fsit.sohojnamaj.database.dao.SuraDao
import com.fsit.sohojnamaj.datastore.PreferencesDataSource
import com.fsit.sohojnamaj.model.UserData
import javax.inject.Inject

class NameRepository @Inject constructor(
    private val nameDao: NameDao
){
    fun getName()=nameDao.getName()
}