package com.fadlurahmanf.starter_app_mvp.ui.auth.presenter

import com.fadlurahmanf.starter_app_mvp.base.BaseView
import com.fadlurahmanf.starter_app_mvp.data.response.auth.LoginResponse

interface LoginContract {
    interface View:BaseView{
        fun loginSuccess(response: LoginResponse)
        fun loginFailed(message:String?)
    }
    interface Presenter{
        fun login(email:String, password:String)
    }
}