package com.fadlurahmanf.starter_app_mvp.ui.home

import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseMvpActivity
import com.fadlurahmanf.starter_app_mvp.data.response.auth.LoginResponse
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyGroupResponse
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyTrainingResponse
import com.fadlurahmanf.starter_app_mvp.databinding.ActivityLandingPageBinding
import com.fadlurahmanf.starter_app_mvp.di.component.HomeComponent
import com.fadlurahmanf.starter_app_mvp.ui.home.presenter.LandingPageContract
import com.fadlurahmanf.starter_app_mvp.ui.home.presenter.LandingPagePresenter
import javax.inject.Inject

class LandingPageActivity : BaseMvpActivity<LandingPagePresenter,ActivityLandingPageBinding>(ActivityLandingPageBinding::inflate),
    LandingPageContract.View {
    @Inject
    lateinit var presenter: LandingPagePresenter

    override fun initPresenterView() {
        presenter.view = this
    }

    private lateinit var component:HomeComponent
    override fun injectView() {
        component = (applicationContext as BaseApp).appComponent.homeComponent().create()
        component.inject(this)
    }

    override fun setup() {
        supportActionBar?.hide()
        setStatusBarStyle(R.color.red, false)
        presenter.getMyGroupAndMySubscription()
        presenter.getMyTraining()
    }

    override fun myGroupAndSubscriptionLoading() {
        
    }

    override fun myGroupAndMySubscriptionLoaded(
        myGroups: List<MyGroupResponse>,
        mySubscriptions: List<LoginResponse.User.Subscription>
    ) {

    }

    override fun myGroupAndMySubscriptionError(message: String?) {
        
    }

    override fun getTrainingLoading() {
        
    }

    override fun getTrainingLoaded(list: List<MyTrainingResponse>) {
        
    }

    override fun myTrainingFailed(message: String?) {
        
    }

}