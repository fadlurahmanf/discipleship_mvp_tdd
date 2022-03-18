package com.fadlurahmanf.starter_app_mvp

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.fadlurahmanf.starter_app_mvp.core.services.AppWorkerFactory
import com.fadlurahmanf.starter_app_mvp.di.component.DaggerApplicationComponent
import javax.inject.Inject


class BaseApp : Application() {
    val appComponent = DaggerApplicationComponent.factory().create(this)

    @Inject
    lateinit var workerFactory: AppWorkerFactory

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)

        WorkManager.initialize(this, Configuration.Builder()
            .setWorkerFactory(workerFactory).build())
    }
}