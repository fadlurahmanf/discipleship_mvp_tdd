package com.fadlurahmanf.starter_app_mvp.di.component

import com.fadlurahmanf.starter_app_mvp.ui.auth.ForgotPasswordActivity
import com.fadlurahmanf.starter_app_mvp.ui.auth.LoginActivity
import com.fadlurahmanf.starter_app_mvp.ui.auth.ResetPasswordActivity
import dagger.Subcomponent
import javax.inject.Singleton

//@Singleton
@Subcomponent
interface AuthComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():AuthComponent
    }

    fun inject(activity:LoginActivity)
    fun inject(activity:ForgotPasswordActivity)
    fun inject(activity: ResetPasswordActivity)
}