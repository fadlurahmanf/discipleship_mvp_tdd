package com.fadlurahmanf.starter_app_mvp.ui.core.dialog

import com.fadlurahmanf.starter_app_mvp.base.BaseDialog
import com.fadlurahmanf.starter_app_mvp.databinding.DialogTransparentBinding

class TransparentDialog:BaseDialog<DialogTransparentBinding>(DialogTransparentBinding::inflate) {
    companion object{
        const val IS_CANCELABLE = "IS_CANCELABLE"
    }

    override fun setup() {
        var isCancelable:Boolean = arguments?.getBoolean(IS_CANCELABLE)?:false
        dialog?.setCancelable(isCancelable)
    }

    override fun injectView() {

    }
}