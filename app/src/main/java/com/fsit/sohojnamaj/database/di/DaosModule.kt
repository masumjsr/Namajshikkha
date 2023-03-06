package com.fsit.sohojnamaj.database.di

import com.fsit.sohojnamaj.database.AppDatabase
import com.fsit.sohojnamaj.database.dao.PrayerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providePrayerDao(
        database:AppDatabase
    ): PrayerDao = database.getPrayerDao()
}