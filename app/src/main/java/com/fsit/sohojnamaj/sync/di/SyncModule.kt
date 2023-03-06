package com.fsit.sohojnamaj.sync.di
import com.fsit.sohojnamaj.util.sync.SyncStatusMonitor
import com.fsit.sohojnamaj.sync.status.WorkManagerSyncStatusMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SyncModule {
    @Binds
    fun bindsSyncStatusMonitor(
        syncStatusMonitor: WorkManagerSyncStatusMonitor
    ): SyncStatusMonitor
}