package com.fadlurahmanf.starter_app_mvp.di.component

import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.mynotes.BibleVerseActivity
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.mynotes.MyNotesActivity
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.mynotes.tab.CuratedNotesFragment
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.mynotes.tab.MyNotesFragment
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Subcomponent
interface SideMenuComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():SideMenuComponent
    }

    fun inject(activity:MyNotesActivity)
    fun inject(fragment:MyNotesFragment)
    fun inject(fragment:CuratedNotesFragment)
    fun inject(activity:BibleVerseActivity)
}