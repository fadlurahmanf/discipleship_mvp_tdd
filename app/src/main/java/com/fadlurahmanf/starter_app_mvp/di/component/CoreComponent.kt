package com.fadlurahmanf.starter_app_mvp.di.component

import com.fadlurahmanf.starter_app_mvp.ui.core.SplashActivity
import com.fadlurahmanf.starter_app_mvp.ui.guest_mode.GuestModeActivity
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Subcomponent
interface CoreComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): CoreComponent
    }

    fun inject(activity: SplashActivity)
    fun inject(activity: GuestModeActivity)
}