package com.fsit.sohojnamaj.data.repository

import com.fsit.sohojnamaj.database.dao.SubCategoryDao
import com.fsit.sohojnamaj.database.dao.SuraDao
import com.fsit.sohojnamaj.database.dao.TypeOneItemDao
import com.fsit.sohojnamaj.database.dao.TypeTwoItemDao
import com.fsit.sohojnamaj.datastore.PreferencesDataSource
import com.fsit.sohojnamaj.model.UserData
import javax.inject.Inject

class TypeTwoRepository @Inject constructor(
    private val typeTwoItemDao: TypeTwoItemDao
){
    fun getTypeTwo(id:Int)=typeTwoItemDao.getTypeTwo(id)
}