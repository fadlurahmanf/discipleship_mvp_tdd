package com.fadlurahmanf.starter_app_mvp.ui.guest_mode

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseMvpActivity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AppRepository
import com.fadlurahmanf.starter_app_mvp.data.response.core.TestimonialResponse
import com.fadlurahmanf.starter_app_mvp.databinding.ActivityGuestModeBinding
import com.fadlurahmanf.starter_app_mvp.di.component.CoreComponent
import com.fadlurahmanf.starter_app_mvp.ui.auth.LoginActivity
import com.fadlurahmanf.starter_app_mvp.ui.guest_mode.adapter.TestimonialAdapter
import com.fadlurahmanf.starter_app_mvp.ui.guest_mode.presenter.GuestModeContract
import com.fadlurahmanf.starter_app_mvp.ui.guest_mode.presenter.GuestModePresenter
import com.fadlurahmanf.starter_app_mvp.ui.home.BaseDrawer
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.SettingActivity
import javax.inject.Inject

class GuestModeActivity : BaseMvpActivity<GuestModePresenter, ActivityGuestModeBinding>(ActivityGuestModeBinding::inflate),
    GuestModeContract.View {
    @Inject
    lateinit var presenter: GuestModePresenter

    private lateinit var component: CoreComponent


    override fun initPresenterView() {
        presenter.view = this
    }

    override fun injectView() {
        component = (applicationContext as BaseApp).appComponent.coreComponent().create()
        component.inject(this)
    }

    override fun setup() {
        setScreenStyle(R.color.white, true, isFullScreen = true)
        supportActionBar?.hide()
        binding?.toolbar?.ivToolbar?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_menu_red))
        binding?.toolbar?.ivToolbar?.setBackgroundResource(R.color.white)
        presenter.getTestimonial()
        initAction()
        initText()
    }

    @Inject
    lateinit var appRepository: AppRepository

    override fun setText() {
        super.setText()
        initText()
    }

    override fun onBackPressed() {
        if (binding?.drawerLayout?.isDrawerOpen(GravityCompat.START) == true){
            BaseDrawer.openCloseDrawer(binding?.drawerLayout)
        }else{
            super.onBackPressed()
        }
    }

    private fun initText() {
        binding?.tvBrowse?.text = appRepository.languageResponse?.guest?.guestHomeBrowseStudy
        binding?.tvStudy?.text = appRepository.languageResponse?.guest?.guestHomeBrowseStudy
    }

    private fun initAction() {
        binding?.floating?.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        binding?.navViewLayout?.llSetting?.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    override fun testimonialLoading() {
        binding?.rvTestimonial?.visibility = View.GONE
        binding?.hsvShimmer?.visibility = View.VISIBLE
        binding?.tvTestimonialError?.visibility = View.GONE
    }

    private lateinit var adapter:TestimonialAdapter
    private var listTestimonial = arrayListOf<TestimonialResponse>()

    override fun testimonialLoaded(list:List<TestimonialResponse>) {
        binding?.rvTestimonial?.visibility = View.VISIBLE
        binding?.hsvShimmer?.visibility = View.GONE
        binding?.tvTestimonialError?.visibility = View.GONE
        listTestimonial.addAll(list)
        adapter = TestimonialAdapter(listTestimonial)
        binding?.rvTestimonial?.adapter = adapter
    }

    override fun testimonialFailed(message: String?) {
        binding?.rvTestimonial?.visibility = View.GONE
        binding?.hsvShimmer?.visibility = View.GONE
        binding?.tvTestimonialError?.visibility = View.VISIBLE
        binding?.tvTestimonialError?.text = message?:""
    }
}