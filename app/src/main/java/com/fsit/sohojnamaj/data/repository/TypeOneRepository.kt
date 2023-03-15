package com.fsit.sohojnamaj.data.repository

import com.fsit.sohojnamaj.database.dao.SubCategoryDao
import com.fsit.sohojnamaj.database.dao.SuraDao
import com.fsit.sohojnamaj.database.dao.TypeOneItemDao
import com.fsit.sohojnamaj.datastore.PreferencesDataSource
import com.fsit.sohojnamaj.model.UserData
import javax.inject.Inject

class TypeOneRepository @Inject constructor(
    private val typeOneItemDao: TypeOneItemDao
){
    fun getTypeOne(id:Int)=typeOneItemDao.getTypeOne(id)
}