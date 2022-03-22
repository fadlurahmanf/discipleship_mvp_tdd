package com.fadlurahmanf.starter_app_mvp.ui.home.presenter

import com.fadlurahmanf.starter_app_mvp.base.BaseView
import com.fadlurahmanf.starter_app_mvp.data.response.study_group.StudyGroupDetailResponse

interface StudyGroupFragmentContract {
    interface View:BaseView{
        fun getStudyGroupDetailLoading()
        fun getStudyGroupDetailSuccess()
        fun getStudyGroupDetailFailed(message:String?)
    }

    interface Presenter{
        fun getStudyGroupDetail()
        fun getStudyGroupDetailSecond()
    }
}