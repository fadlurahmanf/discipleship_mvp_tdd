package com.fadlurahmanf.starter_app_mvp.ui.sidemenu.mynotes.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.mynotes.tab.CuratedNotesFragment
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.mynotes.tab.MyNotesFragment

class MyNotesPagerAdapter(supportFragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(supportFragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> CuratedNotesFragment()
            else -> MyNotesFragment()
        }
    }
}