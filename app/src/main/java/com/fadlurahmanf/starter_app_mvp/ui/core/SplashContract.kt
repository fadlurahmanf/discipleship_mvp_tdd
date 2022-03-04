package com.fadlurahmanf.starter_app_mvp.ui.core

import com.fadlurahmanf.starter_app_mvp.base.BaseView

interface SplashContract {
    interface View:BaseView{
        fun checkUpdateLanguageSuccess()
        fun checkUpdateLanguageFailed(message:String?)
    }
    interface Presenter{
        fun checkUpdateLanguage()
    }
}