package com.fadlurahmanf.starter_app_mvp.di.component

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.di.module.WorkerModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    WorkerModule::class
])
interface ApplicationComponent {
    fun inject(app:BaseApp)

    fun exampleComponent(): ExampleComponent.Factory
    fun coreComponent(): CoreComponent.Factory
    fun authComponent(): AuthComponent.Factory
    fun homeComponent(): HomeComponent.Factory
    fun sideMenuComponent(): SideMenuComponent.Factory

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context):ApplicationComponent
    }
}