package com.fadlurahmanf.starter_app_mvp.ui.home.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fadlurahmanf.starter_app_mvp.ui.home.tab.WaitlistFirstFragment
import com.fadlurahmanf.starter_app_mvp.ui.home.tab.WaitlistSecondFragment

class WaitlistPagerAdapter(supportFragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(supportFragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1 -> WaitlistFirstFragment()
            else -> WaitlistSecondFragment()
        }
    }
}