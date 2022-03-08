package com.fadlurahmanf.starter_app_mvp.ui.home.tab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseFragment
import com.fadlurahmanf.starter_app_mvp.databinding.FragmentGroupBoardBinding
import com.fadlurahmanf.starter_app_mvp.di.component.HomeComponent

class GroupBoardFragment : BaseFragment<FragmentGroupBoardBinding>(FragmentGroupBoardBinding::inflate) {
    private lateinit var component: HomeComponent
    override fun injectView() {
        component = (this.requireActivity().applicationContext as BaseApp).appComponent.homeComponent().create()
        component.inject(this)
    }

    override fun setup() {

    }

}