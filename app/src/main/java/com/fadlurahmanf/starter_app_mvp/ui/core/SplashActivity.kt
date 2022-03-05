package com.fadlurahmanf.starter_app_mvp.ui.core

import android.content.Intent
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseMvpActivity
import com.fadlurahmanf.starter_app_mvp.core.extension.isValidEmail
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AppRepository
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import com.fadlurahmanf.starter_app_mvp.data.repository.example.ExampleRepository
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyGroupResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.CheckUpdateResponse
import com.fadlurahmanf.starter_app_mvp.databinding.ActivitySplashBinding
import com.fadlurahmanf.starter_app_mvp.di.component.CoreComponent
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

    override fun setup() {
        setTransparentStatusBar()
        supportActionBar?.hide()
        setStatusBarStyle(R.color.white)
        presenter.checkUpdateLanguage()
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
    }

    override fun goToLandingPage(data: CheckUpdateResponse) {
        val intent = Intent(this, LandingPageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun checkUpdateLanguageFailed(message:String?) {
        showSnackBar(message)
        val intent = Intent(this, GuestModeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}