package com.fadlurahmanf.starter_app_mvp.base

interface BaseView {
    fun loadingDialog(loadingText:String ?= null, isCancelable:Boolean ?= false)
    fun dismissLoadingDialog()
    fun errorScreen(message:String ?= "")
    fun errorConnection()
    fun forceRestart(message: String?)
}