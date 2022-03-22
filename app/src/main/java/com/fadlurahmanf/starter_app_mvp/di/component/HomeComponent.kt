package com.fadlurahmanf.starter_app_mvp.di.component

import com.fadlurahmanf.starter_app_mvp.ui.home.LandingPageActivity
import com.fadlurahmanf.starter_app_mvp.ui.home.MainActivity
import com.fadlurahmanf.starter_app_mvp.ui.home.WaitlistActivity
import com.fadlurahmanf.starter_app_mvp.ui.home.tab.GroupBoardFragment
import com.fadlurahmanf.starter_app_mvp.ui.home.tab.PrayerRequestFragment
import com.fadlurahmanf.starter_app_mvp.ui.home.tab.StudyGroupFragment
import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent
interface HomeComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():HomeComponent
    }

    fun inject(activity:LandingPageActivity)
    fun inject(activity:WaitlistActivity)
    fun inject(activity:MainActivity)
    fun inject(fragment:GroupBoardFragment)
    fun inject(fragment:PrayerRequestFragment)
    fun inject(fragment:StudyGroupFragment)
}