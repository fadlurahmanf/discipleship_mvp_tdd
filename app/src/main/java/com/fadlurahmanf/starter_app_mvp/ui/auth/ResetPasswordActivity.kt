package com.fadlurahmanf.starter_app_mvp.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.base.BaseActivity
import com.fadlurahmanf.starter_app_mvp.databinding.ActivityResetPasswordBinding
import com.fadlurahmanf.starter_app_mvp.di.component.AuthComponent

class ResetPasswordActivity : BaseActivity<ActivityResetPasswordBinding>(ActivityResetPasswordBinding::inflate) {
    private lateinit var component: AuthComponent
    override fun injectView() {
        component = (applicationContext as BaseApp).appComponent.authComponent().create()
        component.inject(this)
    }

    override fun setup() {
        supportActionBar?.hide()
        initAction()
    }

    private fun initAction() {
        binding?.etlPassword?.setEndIconOnClickListener {
//            binding?.etlPassword?.setEndIconTintList(Col)
        }
    }

}