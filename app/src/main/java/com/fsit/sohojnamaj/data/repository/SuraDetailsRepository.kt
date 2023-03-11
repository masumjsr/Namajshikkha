package com.fsit.sohojnamaj.data.repository

import com.fsit.sohojnamaj.database.dao.SuraDetailsDao
import javax.inject.Inject

class SuraDetailsRepository @Inject constructor(
    val suraDetailsDao: SuraDetailsDao,
){
    fun getSuraDetails(id:Int)=suraDetailsDao.getSuraDetails(id)

}