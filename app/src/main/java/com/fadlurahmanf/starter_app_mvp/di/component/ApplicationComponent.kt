package com.fadlurahmanf.starter_app_mvp.di.component

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.BaseApp
import dagger.BindsInstance
import dagger.Component


@Component(modules = [])
interface ApplicationComponent {
    fun inject(app:BaseApp)

    fun exampleComponent(): ExampleComponent.Factory
    fun coreComponent(): CoreComponent.Factory
    fun authComponent(): AuthComponent.Factory

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context):ApplicationComponent
    }
}