package com.fsit.sohojnamaj.database.di

import com.fsit.sohojnamaj.database.AppDatabase
import com.fsit.sohojnamaj.database.dao.*
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
    fun provideSuraDetailsDao(
        database:AppDatabase
    ): SuraDetailsDao = database.getSuraDetailsDao()
    @Provides
    fun provideSubCategoryDao(
        database:AppDatabase
    ): SubCategoryDao = database.getSubCategoryDao()

    @Provides
    fun provideTypeOneDao(
        database:AppDatabase
    ): TypeOneItemDao = database.getTypeOneDao()

    @Provides
    fun provideTypeTwoDao(
        database:AppDatabase
    ): TypeTwoItemDao = database.getTypeTwoDao()
}