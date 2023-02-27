package com.fsit.sohojnamaj.network

import com.fsit.sohojnamaj.model.PrayerTimeResponse
import kotlinx.coroutines.flow.Flow

interface NetworkDataSource {
    suspend fun getPrayerTime():PrayerTimeResponse
}