package com.fadlurahmanf.starter_app_mvp.di.component

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.di.module.WorkerModule
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.AlarmReceiver
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
    fun studyGroupComponent(): StudyGroupComponent.Factory

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context):ApplicationComponent
    }

    fun inject(receiver:AlarmReceiver)
}