package com.fadlurahmanf.starter_app_mvp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.base.BaseActivity
import com.fadlurahmanf.starter_app_mvp.databinding.ActivityForgotPasswordBinding
import com.fadlurahmanf.starter_app_mvp.di.component.AuthComponent

class ForgotPasswordActivity : BaseActivity<ActivityForgotPasswordBinding>(ActivityForgotPasswordBinding::inflate) {
    private lateinit var component:AuthComponent
    override fun injectView() {
        component = (applicationContext as BaseApp).appComponent.authComponent().create()
        component.inject(this)
    }

    override fun setup() {
        val intent = Intent(this, ResetPasswordActivity::class.java)
        startActivity(intent)
    }

}