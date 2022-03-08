package com.fadlurahmanf.starter_app_mvp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseActivity
import com.fadlurahmanf.starter_app_mvp.databinding.ActivityWaitlistBinding
import com.fadlurahmanf.starter_app_mvp.di.component.HomeComponent
import com.fadlurahmanf.starter_app_mvp.ui.home.adapter.WaitlistPagerAdapter
import com.fadlurahmanf.starter_app_mvp.ui.home.tab.WaitlistFirstFragment
import com.google.android.material.tabs.TabLayoutMediator

class WaitlistActivity : BaseActivity<ActivityWaitlistBinding>(ActivityWaitlistBinding::inflate) {
    private lateinit var homeComponent: HomeComponent

    override fun injectView() {
        homeComponent = (applicationContext as BaseApp).appComponent.homeComponent().create()
        homeComponent.inject(this)
    }

    override fun setup() {
        supportActionBar?.hide()
        setStatusBarStyle(R.color.red, false)
        initPagerAdapter()
    }

    private lateinit var adapter: WaitlistPagerAdapter
    private var fragment = WaitlistFirstFragment()

    private fun initPagerAdapter() {
        var adapter = WaitlistPagerAdapter(supportFragmentManager, lifecycle)
        binding?.viewPager?.adapter = adapter
        TabLayoutMediator(binding!!.tabLayout, binding!!.viewPager){ tab, position ->
            tab.text = position.toString()
//            binding.tabLayout.text
        }.attach()
    }
}