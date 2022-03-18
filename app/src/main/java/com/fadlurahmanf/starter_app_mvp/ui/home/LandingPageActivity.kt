package com.fadlurahmanf.starter_app_mvp.ui.home

import android.content.Intent
import android.view.View
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.base.BaseMvpActivity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import com.fadlurahmanf.starter_app_mvp.data.response.auth.LoginResponse
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyGroupResponse
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyTrainingResponse
import com.fadlurahmanf.starter_app_mvp.databinding.ActivityLandingPageBinding
import com.fadlurahmanf.starter_app_mvp.di.component.HomeComponent
import com.fadlurahmanf.starter_app_mvp.ui.auth.LoginActivity
import com.fadlurahmanf.starter_app_mvp.ui.guest_mode.GuestModeActivity
import com.fadlurahmanf.starter_app_mvp.ui.home.adapter.MyGroupAdapter
import com.fadlurahmanf.starter_app_mvp.ui.home.presenter.LandingPageContract
import com.fadlurahmanf.starter_app_mvp.ui.home.presenter.LandingPagePresenter
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.SettingActivity
import javax.inject.Inject

class LandingPageActivity : BaseMvpActivity<LandingPagePresenter,ActivityLandingPageBinding>(ActivityLandingPageBinding::inflate),
    LandingPageContract.View, MyGroupAdapter.StudyGroupCallBack {
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
        setScreenStyle(isLight = false, isFullScreen = true)
        initAction()
        initAdapter()
        presenter.getMyGroupAndMySubscription()
        presenter.getMyTraining()
    }

    private fun initAction() {
        binding?.navViewLayout?.btnLogin?.setOnClickListener {
            authRepository.clearAll()
            val intent = Intent(this, GuestModeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

            val intent2 = Intent(this, LoginActivity::class.java)
            startActivity(intent2)
            finish()
        }

        binding?.navViewLayout?.llSetting?.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private lateinit var adapter:MyGroupAdapter
    private var myGroups:ArrayList<MyGroupResponse> = arrayListOf()
    private fun initAdapter() {
        adapter = MyGroupAdapter(myGroups)
        adapter.setOnItemClickCallback(this)
        binding?.rvStudyGroup?.adapter = adapter
        refreshRecycleView()
    }


    private fun refreshRecycleView(){
        adapter.notifyDataSetChanged()
    }

    override fun myGroupAndSubscriptionLoading() {
        binding?.studyGroupShimmer?.visibility = View.VISIBLE
        binding?.rvStudyGroup?.visibility = View.GONE
    }

    override fun myGroupAndMySubscriptionLoaded(
        myGroups: List<MyGroupResponse>,
        mySubscriptions: List<LoginResponse.User.Subscription>
    ) {
        binding?.studyGroupShimmer?.visibility = View.GONE
        binding?.rvStudyGroup?.visibility = View.VISIBLE
        this.myGroups.clear()
        this.myGroups.addAll(myGroups)
        refreshRecycleView()
    }

    override fun myGroupAndMySubscriptionError(message: String?) {
        showSnackBar(message)
    }

    override fun getTrainingLoading() {
        
    }

    override fun getTrainingLoaded(list: List<MyTrainingResponse>) {
        
    }

    override fun myTrainingFailed(message: String?) {
        
    }

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onActiveStudyGroupCallBack(group: MyGroupResponse?) {
        if (group != null){
            authRepository.groupChosen = group
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}