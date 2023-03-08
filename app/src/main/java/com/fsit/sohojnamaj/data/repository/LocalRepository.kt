package com.fsit.sohojnamaj.data.repository

import com.fsit.sohojnamaj.datastore.PreferencesDataSource
import com.fsit.sohojnamaj.PrayerTime
import com.fsit.sohojnamaj.model.UserData
import javax.inject.Inject

class LocalRepository @Inject constructor(
    val dataSource: PreferencesDataSource
){
    val userData = dataSource.userdata
    suspend fun updateUserData(userData: UserData)=dataSource.updateUserData(userData)
}