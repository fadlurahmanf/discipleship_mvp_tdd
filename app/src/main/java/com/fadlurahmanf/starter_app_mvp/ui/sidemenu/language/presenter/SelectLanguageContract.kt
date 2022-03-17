package com.fadlurahmanf.starter_app_mvp.ui.sidemenu.language.presenter

import com.fadlurahmanf.starter_app_mvp.base.BaseView
import com.fadlurahmanf.starter_app_mvp.data.response.core.ParameterLanguageResponse

interface SelectLanguageContract {
    interface View:BaseView{
        fun getParameterLanguageLoading()
        fun getParameterSuccess(listParameterLanguage: List<ParameterLanguageResponse>)
        fun getParameterFailed(message:String?)

        fun selectLanguageLoading()
        fun selectLanguageSuccess(param: ParameterLanguageResponse)
        fun selectLanguageFailed(message: String?)
    }
    interface Presenter{
        fun getParameterLanguage()
        fun selectLanguage(param: ParameterLanguageResponse)
    }
}