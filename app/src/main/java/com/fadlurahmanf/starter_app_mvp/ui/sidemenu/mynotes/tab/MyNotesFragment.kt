package com.fadlurahmanf.starter_app_mvp.ui.sidemenu.mynotes.tab

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseFragment
import com.fadlurahmanf.starter_app_mvp.databinding.FragmentMyNotesBinding
import com.fadlurahmanf.starter_app_mvp.di.component.SideMenuComponent
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.mynotes.BibleVerseActivity
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.mynotes.adapter.MyNotesAdapter

class MyNotesFragment : BaseFragment<FragmentMyNotesBinding>(FragmentMyNotesBinding::inflate),
    MyNotesAdapter.ItemClickCallBack {
    private lateinit var component:SideMenuComponent
    override fun injectView() {
        component = appComponent.sideMenuComponent().create()
        component.inject(this)
    }

    private lateinit var adapter:MyNotesAdapter
    override fun setup() {
        initAdapter()
    }

    private fun initAdapter() {
        adapter = MyNotesAdapter(arrayListOf("", "", ""))
        adapter.setOnItemClickCallBack(this)
        binding?.rv?.adapter = adapter
    }

    override fun onItemClicked() {
        val intent = Intent(requireContext(), BibleVerseActivity::class.java)
        startActivity(intent)
    }

}