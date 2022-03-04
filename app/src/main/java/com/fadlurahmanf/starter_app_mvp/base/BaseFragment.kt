package com.fadlurahmanf.starter_app_mvp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias InflateFragment<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB:ViewBinding>(
    private val inflateFragment:InflateFragment<VB>
):Fragment() {

    private var _binding:VB ?= null
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateFragment.invoke(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        injectView()
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    abstract fun injectView()

    abstract fun setup()
}