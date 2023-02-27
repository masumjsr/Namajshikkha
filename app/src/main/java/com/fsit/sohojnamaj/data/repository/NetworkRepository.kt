package com.fsit.sohojnamaj.data.repository

import com.fsit.sohojnamaj.network.NetworkDataSource
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun prayerResponse() =  networkDataSource.getPrayerTime()
}