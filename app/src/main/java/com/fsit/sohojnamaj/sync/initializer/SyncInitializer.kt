package com.fsit.sohojnamaj.sync.initializer

import android.content.Context
import androidx.startup.AppInitializer
import androidx.startup.Initializer
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import androidx.work.WorkManagerInitializer
import com.fsit.sohojnamaj.sync.workers.SyncWorker

object Sync {
    fun initialize(context: Context){
        AppInitializer.getInstance(context)
            .initializeComponent(SyncInitializer::class.java)
    }
    fun refresh(context: Context){
        WorkManager.getInstance(context).apply {

            enqueueUniqueWork(
                SyncWorkName,
                ExistingWorkPolicy.KEEP,
                SyncWorker.startUpSyncWork()
            )
        }
    }
}
internal const val SyncWorkName = "SyncWorkName"

class SyncInitializer : Initializer<Sync> {
    override fun create(context: Context): Sync {
        WorkManager.getInstance(context).apply {

            enqueueUniqueWork(
                SyncWorkName,
                ExistingWorkPolicy.KEEP,
                SyncWorker.startUpSyncWork()
            )
        }
        return Sync
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(WorkManagerInitializer::class.java)

}