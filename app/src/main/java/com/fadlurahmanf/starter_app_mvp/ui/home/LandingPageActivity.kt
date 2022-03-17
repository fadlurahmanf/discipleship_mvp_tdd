package com.fadlurahmanf.starter_app_mvp.ui.home

import android.content.Intent
import android.view.View
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseMvpActivity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import com.fadlurahmanf.starter_app_mvp.data.response.auth.LoginResponse
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyGroupResponse
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyTrainingResponse
import com.fadlurahmanf.starter_app_mvp.databinding.ActivityLandingPageBinding
import com.fadlurahmanf.starter_app_mvp.di.component.HomeComponent
import com.fadlurahmanf.starter_app_mvp.ui.home.adapter.MyGroupAdapter
import com.fadlurahmanf.starter_app_mvp.ui.home.presenter.LandingPageContract
import com.fadlurahmanf.starter_app_mvp.ui.home.presenter.LandingPagePresenter
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
        supportActionBar?.hide()
        setScreenStyle(R.color.red, isLight = false)
        initAdapter()
        presenter.getMyGroupAndMySubscription()
        presenter.getMyTraining()
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