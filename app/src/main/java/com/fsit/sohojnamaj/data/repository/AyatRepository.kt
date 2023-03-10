package com.fsit.sohojnamaj.data.repository

import com.fsit.sohojnamaj.database.dao.AyatArDao
import com.fsit.sohojnamaj.database.dao.AyatBnDao
import com.fsit.sohojnamaj.database.dao.SuraDao
import com.fsit.sohojnamaj.datastore.PreferencesDataSource
import com.fsit.sohojnamaj.model.UserData
import javax.inject.Inject

class AyatRepository @Inject constructor(
    val arabicAyat: AyatArDao,
    val banglaAyat: AyatBnDao
){
    fun getArabicAyat(id:Int)=arabicAyat.getAyatAr(id)
    fun getBangaAyat(id:Int)=banglaAyat.getAyatBn(id)

}