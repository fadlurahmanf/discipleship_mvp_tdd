package com.fadlurahmanf.starter_app_mvp.di.module

import androidx.work.ListenableWorker
import com.fadlurahmanf.starter_app_mvp.core.services.ChildWorkerFactory
import com.fadlurahmanf.starter_app_mvp.ui.core.worker.AlarmWorker
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class WorkerKey(val value:KClass<out ListenableWorker>)

@Module
abstract class WorkerModule{
    @Binds
    @IntoMap
    @WorkerKey(AlarmWorker::class)
    abstract fun bindMyWorker(factory: AlarmWorker.Factory):ChildWorkerFactory
}