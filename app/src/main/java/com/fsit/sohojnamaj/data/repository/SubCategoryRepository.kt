package com.fsit.sohojnamaj.data.repository

import com.fsit.sohojnamaj.database.dao.SubCategoryDao
import com.fsit.sohojnamaj.database.dao.SuraDao
import com.fsit.sohojnamaj.datastore.PreferencesDataSource
import com.fsit.sohojnamaj.model.UserData
import javax.inject.Inject

class SubCategoryRepository @Inject constructor(
    private val subCategoryDao: SubCategoryDao
){
    fun getSubCategory(id:Int)=subCategoryDao.getSubCategory(id)
}