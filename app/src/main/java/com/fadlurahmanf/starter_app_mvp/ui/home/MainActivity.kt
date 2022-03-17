package com.fadlurahmanf.starter_app_mvp.ui.home

import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseActivity
import com.fadlurahmanf.starter_app_mvp.databinding.ActivityMainBinding
import com.fadlurahmanf.starter_app_mvp.di.component.HomeComponent
import com.fadlurahmanf.starter_app_mvp.ui.home.tab.GroupBoardFragment
import com.fadlurahmanf.starter_app_mvp.ui.home.tab.PrayerRequestFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private lateinit var component:HomeComponent
    private var currentIndexFragment = 0
    override fun injectView() {
        component = (applicationContext as BaseApp).appComponent.homeComponent().create()
        component.inject(this)
    }

    override fun setup() {
        supportActionBar?.hide()
        setScreenStyle(R.color.red, isLight = true, isFullScreen = true)
        addFragment(0)
        initAction()
    }

    private fun initAction() {
        binding?.llHome?.setOnClickListener {
            if (currentIndexFragment != 0){
                addFragment(0)
            }
        }
        binding?.llRequest?.setOnClickListener {
            if (currentIndexFragment != 2){
                addFragment(2)
            }
        }
    }

    fun openCloseDrawer(){
        if (binding?.drawerLayout?.isDrawerOpen(GravityCompat.START) == true){
            binding?.drawerLayout?.closeDrawer(GravityCompat.START)
        }else{
            binding?.drawerLayout?.openDrawer(GravityCompat.START)
        }
    }

    private fun addFragment(index:Int) {
        var beginTransaction = supportFragmentManager.beginTransaction()
        when(index){
            2 -> {
                currentIndexFragment = 2
                refreshStyleBottomNavBar()
                binding?.ivRequest?.setImageResource(R.drawable.ic_request_active)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding?.tvRequest?.setTextAppearance(R.style.Font_Bold_12)
                }
                binding?.tvRequest?.setTextColor(ContextCompat.getColor(this, R.color.red))
                beginTransaction.replace(R.id.fl, PrayerRequestFragment())
            }
            0 -> {
                currentIndexFragment = 0
                refreshStyleBottomNavBar()
                binding?.ivGroupBoard?.setImageResource(R.drawable.ic_home_active)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding?.tvGroupBoard?.setTextAppearance(R.style.Font_Bold_12)
                }
                binding?.tvGroupBoard?.setTextColor(ContextCompat.getColor(this, R.color.red))
                beginTransaction.replace(R.id.fl, GroupBoardFragment())
            }
        }
        beginTransaction.commit()
    }

    private fun refreshStyleBottomNavBar(){
        binding?.ivGroupBoard?.setImageResource(R.drawable.ic_home_inactive)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding?.tvGroupBoard?.setTextAppearance(R.style.Font_Regular_14)
        }
        binding?.tvGroupBoard?.setTextColor(ContextCompat.getColor(this, R.color.medium_grey))

        binding?.ivRequest?.setImageResource(R.drawable.ic_request_inactive)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding?.tvRequest?.setTextAppearance(R.style.Font_Regular_14)
        }
        binding?.tvRequest?.setTextColor(ContextCompat.getColor(this, R.color.medium_grey))
    }
}