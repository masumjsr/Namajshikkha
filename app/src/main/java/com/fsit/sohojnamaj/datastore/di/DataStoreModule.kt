package com.fsit.sohojnamaj.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.fsit.sohojnamaj.PrayerPreferences
import com.fsit.sohojnamaj.UserPreferences
import com.fsit.sohojnamaj.datastore.PrayerPreferencesSerializer
import com.fsit.sohojnamaj.network.Dispatcher
import com.fsit.sohojnamaj.network.Dispatchers
import com.fsit.sohojnamaj.datastore.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context : Context,
        @Dispatcher(Dispatchers.IO) ioDispatcher: CoroutineDispatcher,
        userPreferencesSerializer: UserPreferencesSerializer
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(ioDispatcher + SupervisorJob())
        ){

            context.dataStoreFile("user_preferences.pb")
        }

    @Provides
    @Singleton
    fun providesPrayerPreferencesDataStore(
        @ApplicationContext context : Context,
        @Dispatcher(Dispatchers.IO) ioDispatcher: CoroutineDispatcher,
        PrayerPreferencesSerializer: PrayerPreferencesSerializer
    ): DataStore<PrayerPreferences> =
        DataStoreFactory.create(
            serializer = PrayerPreferencesSerializer,
            scope = CoroutineScope(ioDispatcher + SupervisorJob())
        ){

            context.dataStoreFile("prayer_preferences.pb")
        }
}