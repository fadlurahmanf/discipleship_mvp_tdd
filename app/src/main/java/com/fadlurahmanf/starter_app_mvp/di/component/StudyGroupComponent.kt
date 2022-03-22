package com.fadlurahmanf.starter_app_mvp.di.component

import com.fadlurahmanf.starter_app_mvp.ui.study_group.CreateStudyGroupDetailActivity
import dagger.Subcomponent

@Subcomponent
interface StudyGroupComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():StudyGroupComponent
    }

    fun inject(activity:CreateStudyGroupDetailActivity)
}