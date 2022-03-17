package com.fadlurahmanf.starter_app_mvp.ui.sidemenu.mynotes

import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseActivity
import com.fadlurahmanf.starter_app_mvp.databinding.ActivityMyNotesBinding
import com.fadlurahmanf.starter_app_mvp.di.component.SideMenuComponent
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.mynotes.adapter.MyNotesPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MyNotesActivity : BaseActivity<ActivityMyNotesBinding>(ActivityMyNotesBinding::inflate) {
    private lateinit var component:SideMenuComponent

    override fun injectView() {
        component = appComponent.sideMenuComponent().create()
        component.inject(this)
    }

    override fun setup() {
        supportActionBar?.hide()
        setScreenStyle(R.color.red, false)
//        binding?.toolbar?
        initPagerAdapter()
    }

    private lateinit var adapter:MyNotesPagerAdapter
    private fun initPagerAdapter() {
        adapter = MyNotesPagerAdapter(supportFragmentManager, lifecycle)
        binding?.viewPager?.adapter = adapter
        TabLayoutMediator(binding!!.tabLayout, binding!!.viewPager){ tab, position ->
            tab.text = position.toString()
        }.attach()
    }

}