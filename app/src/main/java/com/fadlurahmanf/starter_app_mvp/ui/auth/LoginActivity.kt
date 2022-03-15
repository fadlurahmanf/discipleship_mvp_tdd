package com.fadlurahmanf.starter_app_mvp.ui.auth

import android.content.Intent
import android.content.res.ColorStateList
import android.text.method.PasswordTransformationMethod
import android.text.method.SingleLineTransformationMethod
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseMvpActivity
import com.fadlurahmanf.starter_app_mvp.core.extension.isValidEmail
import com.fadlurahmanf.starter_app_mvp.data.response.auth.LoginResponse
import com.fadlurahmanf.starter_app_mvp.databinding.ActivityLoginBinding
import com.fadlurahmanf.starter_app_mvp.di.component.AuthComponent
import com.fadlurahmanf.starter_app_mvp.ui.auth.presenter.LoginContract
import com.fadlurahmanf.starter_app_mvp.ui.auth.presenter.LoginPresenter
import com.fadlurahmanf.starter_app_mvp.ui.home.LandingPageActivity
import javax.inject.Inject

class LoginActivity : BaseMvpActivity<LoginPresenter, ActivityLoginBinding>(ActivityLoginBinding::inflate),
    LoginContract.View {
    private lateinit var component:AuthComponent

    @Inject
    lateinit var presenter: LoginPresenter

    override fun initPresenterView() {
        presenter.view = this
    }

    override fun injectView() {
        component = (applicationContext as BaseApp).appComponent.authComponent().create()
        component.inject(this)
    }

    private var passwordVisible = false

    override fun setup() {
        supportActionBar?.hide()
        setStatusBarStyle(R.color.white, true)
        initAction()
        initListener()
        checkButtonEnabled()
    }

    private fun initListener() {
        binding?.etEmail?.doAfterTextChanged {
            checkButtonEnabled()
        }

        binding?.etPassword?.doAfterTextChanged {
            checkButtonEnabled()
        }
    }

    private fun checkButtonEnabled(){
        var isEmailError: Boolean? = null
        var isPasswordError :Boolean? = null
        if (binding?.etEmail?.text?.isEmpty() == true){
            isEmailError = null
            binding?.tvEmailError?.visibility = View.GONE
        } else if (binding?.etEmail?.text?.toString()?.isValidEmail() == true){
            isEmailError = false
            binding?.tvEmailError?.visibility = View.GONE
        } else {
            isEmailError = true
            binding?.tvEmailError?.visibility = View.VISIBLE
        }

        if (binding?.etPassword?.text?.isEmpty() == true){
            isPasswordError = null
        } else isPasswordError = binding?.etPassword?.text?.length?:0 < 6

        binding?.btnLogin?.isEnabled = isEmailError == false && isPasswordError == false

        changeButtonBackground()
    }

    private fun changeButtonBackground(){
        if (binding?.btnLogin?.isEnabled == true){
            binding?.btnLogin?.background = ContextCompat.getDrawable(this, R.drawable.gradient_red_corner_10)
            binding?.btnLogin?.setTextColor(ContextCompat.getColor(this, R.color.white))
        }else{
            binding?.btnLogin?.background = ContextCompat.getDrawable(this, R.drawable.grey_corner_10)
            binding?.btnLogin?.setTextColor(ContextCompat.getColor(this, R.color.medium_grey))
        }
    }

    private fun initAction(){

        binding?.etlPassword?.setEndIconOnClickListener {
            if (passwordVisible){
                binding?.etlPassword?.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.medium_grey)))
                binding?.etPassword?.transformationMethod = PasswordTransformationMethod.getInstance()
                passwordVisible = false
                binding?.etPassword?.setSelection(binding?.etPassword?.text?.length?:0)
            }else{
                binding?.etlPassword?.setEndIconTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gold)))
                binding?.etPassword?.transformationMethod = SingleLineTransformationMethod()
                passwordVisible = true
                binding?.etPassword?.setSelection(binding?.etPassword?.text?.length?:0)
            }
        }

        binding?.btnLogin?.setOnClickListener {
            presenter.login(binding?.etEmail!!.text.toString(), binding?.etPassword!!.text.toString())
        }

        binding?.tvForgotPassword?.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun loginSuccess(response: LoginResponse) {
        val intent = Intent(this, LandingPageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun loginFailed(message: String?) {
        showOkDialog(title = "Oops..", content = message)
    }

}