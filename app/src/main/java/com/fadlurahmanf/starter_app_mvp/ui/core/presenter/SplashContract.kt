package com.fadlurahmanf.starter_app_mvp.ui.core.presenter

import com.fadlurahmanf.starter_app_mvp.base.BaseView
import com.fadlurahmanf.starter_app_mvp.data.response.core.CheckUpdateResponse

interface SplashContract {
    interface View:BaseView{
        fun goToGuestMode(data:CheckUpdateResponse)
        fun goToLandingPage(data: CheckUpdateResponse)
        fun checkUpdateLanguageFailed(message:String?)
    }
    interface Presenter{
        fun checkUpdateLanguage()
    }
}