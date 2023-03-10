package com.fsit.sohojnamaj.database.di

import com.fsit.sohojnamaj.database.AppDatabase
import com.fsit.sohojnamaj.database.dao.AyatArDao
import com.fsit.sohojnamaj.database.dao.AyatBnDao
import com.fsit.sohojnamaj.database.dao.PrayerDao
import com.fsit.sohojnamaj.database.dao.SuraDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Single

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun provideSuraDao(
        database:AppDatabase
    ): SuraDao = database.getSuraDao()

    @Provides
    fun provideAyatArDao(
        database:AppDatabase
    ): AyatArDao = database.getAyatArDao()
    @Provides
    fun provideAyatBnDao(
        database:AppDatabase
    ): AyatBnDao = database.getAyatBnDao()
}