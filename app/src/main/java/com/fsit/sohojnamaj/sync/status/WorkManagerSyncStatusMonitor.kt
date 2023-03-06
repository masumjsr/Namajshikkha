package com.fsit.sohojnamaj.sync.status

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.fsit.sohojnamaj.sync.Transformations
import com.fsit.sohojnamaj.sync.initializer.SyncWorkName
import com.fsit.sohojnamaj.util.sync.SyncStatusMonitor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import javax.inject.Inject

class WorkManagerSyncStatusMonitor @Inject constructor(
    @ApplicationContext context: Context
) : SyncStatusMonitor {

    override val isSyncing: Flow<Boolean> =
        Transformations.map(
            WorkManager.getInstance(context).getWorkInfosForUniqueWorkLiveData(SyncWorkName),
            MutableList<WorkInfo>::anyRunning
        )
            .asFlow()
            .conflate()
}
private val List<WorkInfo>.anyRunning get() = any {it.state == WorkInfo.State.RUNNING}
