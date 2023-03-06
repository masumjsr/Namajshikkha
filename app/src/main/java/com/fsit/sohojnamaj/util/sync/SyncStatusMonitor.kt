package com.fsit.sohojnamaj.util.sync

import kotlinx.coroutines.flow.Flow

interface SyncStatusMonitor {
    val isSyncing: Flow<Boolean>

}