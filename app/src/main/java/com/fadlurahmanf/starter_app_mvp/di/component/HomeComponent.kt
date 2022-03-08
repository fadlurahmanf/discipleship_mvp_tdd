package com.fadlurahmanf.starter_app_mvp.di.component

import com.fadlurahmanf.starter_app_mvp.ui.home.LandingPageActivity
import com.fadlurahmanf.starter_app_mvp.ui.home.WaitlistActivity
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Subcomponent
interface HomeComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():HomeComponent
    }

    fun inject(activity:LandingPageActivity)
    fun inject(activity:WaitlistActivity)
}