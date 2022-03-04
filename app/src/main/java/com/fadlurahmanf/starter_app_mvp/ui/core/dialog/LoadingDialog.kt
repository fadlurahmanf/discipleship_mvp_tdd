package com.fadlurahmanf.starter_app_mvp.ui.core.dialog

import com.fadlurahmanf.starter_app_mvp.base.BaseDialog
import com.fadlurahmanf.starter_app_mvp.databinding.DialogLoadingBinding
import com.fadlurahmanf.starter_app_mvp.di.component.CoreComponent

class LoadingDialog:BaseDialog<DialogLoadingBinding>(DialogLoadingBinding::inflate) {
    companion object{
        const val TEXT = "TEXT"
        const val IS_CANCELABLE = "IS_CANCELABLE"
    }
    override fun setup() {
        initData()
    }

    private fun initData() {
        var argument = this.arguments

        var loadingText = argument?.getString(TEXT, "Loading...")
        var isCancelable = argument?.getBoolean(IS_CANCELABLE)

        binding?.tvContent?.text = loadingText
        dialog?.setCanceledOnTouchOutside(isCancelable?:false)
    }

    override fun injectView() {}
}