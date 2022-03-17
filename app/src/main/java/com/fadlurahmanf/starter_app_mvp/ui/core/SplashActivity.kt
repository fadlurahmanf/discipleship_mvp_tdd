package com.fadlurahmanf.starter_app_mvp.ui.core

import android.content.Intent
import android.net.Uri
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseMvpActivity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import com.fadlurahmanf.starter_app_mvp.data.response.core.CheckUpdateResponse
import com.fadlurahmanf.starter_app_mvp.databinding.ActivitySplashBinding
import com.fadlurahmanf.starter_app_mvp.di.component.CoreComponent
import com.fadlurahmanf.starter_app_mvp.ui.auth.ResetPasswordActivity
import com.fadlurahmanf.starter_app_mvp.ui.core.presenter.SplashContract
import com.fadlurahmanf.starter_app_mvp.ui.core.presenter.SplashPresenter
import com.fadlurahmanf.starter_app_mvp.ui.guest_mode.GuestModeActivity
import com.fadlurahmanf.starter_app_mvp.ui.home.LandingPageActivity
import javax.inject.Inject

class SplashActivity : BaseMvpActivity<SplashPresenter, ActivitySplashBinding>(ActivitySplashBinding::inflate),
    SplashContract.View {
    private lateinit var coreComponent: CoreComponent

    override fun injectView() {
        coreComponent = (applicationContext as BaseApp).appComponent.coreComponent().create()
        coreComponent.inject(this)
    }

    @Inject
    lateinit var authRepository: AuthRepository

    private var uriDeepLink : Uri ?= null

    override fun setup() {
        setTransparentStatusBar()
        supportActionBar?.hide()
        setScreenStyle(R.color.white)
        uriDeepLink = intent.data
        authRepository.bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRmZmFqYXJpQGdtYWlsLmNvbSIsInN1YiI6MjIsInJvbGUiOiJwYXJ0aWNpcGFudCIsImlhdCI6MTY0NjQ1ODczOH0.APr6QtCXFfI2lozVSdFVf43pjs5SgJteMxPJmdSRJj8"
        presenter.checkUpdateLanguage()
    }

    private fun handleDeepLink(){
        if (uriDeepLink != null){
            if (uriDeepLink?.path?.contains("api/user/auth/forgot-password-confirmation") == true){
                val intent = Intent(this, ResetPasswordActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        uriDeepLink = null
    }

    @Inject
    lateinit var presenter: SplashPresenter

    override fun initPresenterView() {
        presenter.view = this
    }

    override fun goToGuestMode(data: CheckUpdateResponse) {
        val intent = Intent(this, GuestModeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
        handleDeepLink()
    }

    override fun goToLandingPage(data: CheckUpdateResponse) {
        val intent = Intent(this, LandingPageActivity::class.java)
//        val intent = Intent(this, MyNotesActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        finish()
        startActivity(intent)
        handleDeepLink()
    }

    override fun checkUpdateLanguageFailed(message:String?) {
        showSnackBar(message)
        val intent = Intent(this, GuestModeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}