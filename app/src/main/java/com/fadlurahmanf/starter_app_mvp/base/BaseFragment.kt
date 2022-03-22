package com.fadlurahmanf.starter_app_mvp.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.di.component.ApplicationComponent
import com.fadlurahmanf.starter_app_mvp.ui.core.SplashActivity
import com.fadlurahmanf.starter_app_mvp.ui.core.dialog.ConfirmDialog
import com.fadlurahmanf.starter_app_mvp.ui.core.dialog.LoadingDialog
import com.fadlurahmanf.starter_app_mvp.ui.core.dialog.OkDialog
import com.google.android.material.snackbar.Snackbar

typealias InflateFragment<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB:ViewBinding>(
    private val inflateFragment:InflateFragment<VB>
):Fragment(), BaseView {

    private lateinit var _appComponent:ApplicationComponent
    val appComponent get() = _appComponent

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
        initAppComponent()
        injectView()
        super.onViewCreated(view, savedInstanceState)
        internalSetup()
        setup()
    }

    private fun initAppComponent() {
        _appComponent = (requireActivity().applicationContext as BaseApp).appComponent
    }

    open fun internalSetup() {}

    abstract fun injectView()

    abstract fun setup()

    private var okDialog: OkDialog?= null
    fun showOkDialog(
        title:String ?= null,
        content:String ?= null,
        isCancelable:Boolean = true,
        okText:String ?= null,
        okListener:() -> Unit ?= {dismissOkDialog()}
    ){
        if (okDialog == null){
            okDialog = OkDialog()
            val bundle = Bundle()
            bundle.putString(OkDialog.TITLE, title)
            bundle.putString(OkDialog.CONTENT, content)
            bundle.putBoolean(OkDialog.IS_CANCELABLE, isCancelable)
            bundle.putString(OkDialog.OK_TEXT, okText)
            okDialog?.arguments = bundle
            okDialog?.setOkListener {
                okListener()
            }
            okDialog?.show(requireActivity().supportFragmentManager, OkDialog::class.java.simpleName)
        }
    }

    fun dismissOkDialog(){
        if (okDialog != null){
            okDialog?.dismiss()
            okDialog = null
        }
    }

    private var confirmDialog: ConfirmDialog?= null
    fun showConfirmDialog(
        title:String ?= null,
        content:String ?= null,
        isCancelable:Boolean = true,
        cancelText:String ?= null,
        confirmText:String ?= null,
        cancelListener:() -> Unit ?= {dismissConfirmDialog()},
        confirmListener:() -> Unit ?= {dismissConfirmDialog()}
    ){
        if (confirmDialog == null){
            confirmDialog = ConfirmDialog()
            val bundle = Bundle()
            bundle.putString(ConfirmDialog.TITLE, title)
            bundle.putString(ConfirmDialog.CONTENT, content)
            bundle.putBoolean(ConfirmDialog.IS_CANCELABLE, isCancelable)
            bundle.putString(ConfirmDialog.CANCEL_TEXT, cancelText)
            bundle.putString(ConfirmDialog.CONFIRM_TEXT, confirmText)
            confirmDialog?.arguments = bundle
            confirmDialog?.setListener(
                cancel = { cancelListener() },
                confirm = { confirmListener() }
            )
            confirmDialog?.show(requireActivity().supportFragmentManager, ConfirmDialog::class.java.simpleName)
        }
    }

    fun dismissConfirmDialog(){
        if (confirmDialog != null){
            confirmDialog?.dismiss()
            confirmDialog = null
        }
    }

    open fun showSnackBar(message: String?, typeLong:Int?=null){
        Snackbar.make(binding!!.root, message?:"", typeLong?: Snackbar.LENGTH_LONG).show()
    }

    override fun forceRestart(message: String?) {
        showOkDialog(
            title = "Oops..",
            content = message?:"",
            okListener = {
                dismissOkDialog()
                val intent = Intent(requireContext(), SplashActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        )
    }

    private var loadingDialog: LoadingDialog?= null
    override fun loadingDialog(loadingText:String?, isCancelable:Boolean) {
        if (loadingDialog == null){
            loadingDialog = LoadingDialog()
            var bundle = Bundle()
            bundle.putString(LoadingDialog.TEXT, loadingText)
            bundle.putBoolean(LoadingDialog.IS_CANCELABLE, isCancelable?:false)
            loadingDialog?.arguments = bundle
            loadingDialog?.show(requireActivity().supportFragmentManager, LoadingDialog::class.java.simpleName)
        }
    }

    override fun dismissLoadingDialog() {
        if (loadingDialog != null){
            loadingDialog?.dismiss()
            loadingDialog = null
        }
    }

    override fun errorScreen(message: String?) {
        showSnackBar(message)
    }

    override fun errorConnection() {}
}