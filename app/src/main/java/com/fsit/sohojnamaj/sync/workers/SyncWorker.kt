package com.fsit.sohojnamaj.sync.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.tracing.traceAsync
import androidx.work.*
import com.fsit.sohojnamaj.data.repository.NetworkRepository
import com.fsit.sohojnamaj.datastore.ChangeListVersions
import com.fsit.sohojnamaj.network.Dispatcher
import com.fsit.sohojnamaj.network.Dispatchers
import com.fsit.sohojnamaj.sync.initializer.SyncConstraints
import com.fsit.sohojnamaj.sync.initializer.syncForegroundInfo
import com.fsit.sohojnamaj.util.sync.Synchronizer
import com.masum.sync.workers.DelegatingWorker
import com.masum.sync.workers.delegatedData
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

@HiltWorker
class SyncWorker @AssistedInject   constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val networkRepository: NetworkRepository,
    @Dispatcher(Dispatchers.IO) private val iODispatcher: CoroutineDispatcher
) : CoroutineWorker (appContext,workerParams ) , Synchronizer {
    override suspend fun getForegroundInfo(): ForegroundInfo =
        appContext.syncForegroundInfo()

    override suspend fun doWork(): Result = withContext(iODispatcher){

        traceAsync("Sync",0){
            val syncSuccessfully = awaitAll(
                async {
                   networkRepository.sync()



                }

            ).all { it }

            if (syncSuccessfully) Result.success()
            else Result.retry()
        }
    }

    override suspend fun getChangeListVersions(): ChangeListVersions {
        return  ChangeListVersions(1)
    }

    override suspend fun updateChangeListVersions(update: ChangeListVersions.() -> ChangeListVersions) {
        TODO("Not yet implemented")
    }

    companion object {
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .setInputData(SyncWorker::class.delegatedData())
            .build()
    }
}