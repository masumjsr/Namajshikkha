package com.fsit.sohojnamaj.network.di

import com.fsit.sohojnamaj.network.NetworkDataSource
import com.fsit.sohojnamaj.network.retrofit.RetrofitNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FlavoredNetworkModule {
    @Binds
    fun RetrofitNetwork.binds(): NetworkDataSource
}