package com.fadlurahmanf.starter_app_mvp.ui.core.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.fadlurahmanf.starter_app_mvp.core.services.ChildWorkerFactory
import com.google.common.util.concurrent.ListenableFuture
import javax.inject.Inject

class AlarmWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return Result.success()
    }

    class Factory @Inject constructor() : ChildWorkerFactory {
        override fun create(context: Context, workerParams: WorkerParameters): ListenableWorker {
            return AlarmWorker(context, workerParams)
        }
    }
}