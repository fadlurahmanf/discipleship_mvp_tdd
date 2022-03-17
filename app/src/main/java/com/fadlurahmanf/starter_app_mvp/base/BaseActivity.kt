package com.fadlurahmanf.starter_app_mvp.base

import android.app.Dialog
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding
import com.fadlurahmanf.starter_app_mvp.BaseApp
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.core.services.ConnectivityReceiver
import com.fadlurahmanf.starter_app_mvp.di.component.ApplicationComponent
import com.fadlurahmanf.starter_app_mvp.ui.core.SplashActivity
import com.fadlurahmanf.starter_app_mvp.ui.core.dialog.ConfirmDialog
import com.fadlurahmanf.starter_app_mvp.ui.core.dialog.LoadingDialog
import com.fadlurahmanf.starter_app_mvp.ui.core.dialog.OkDialog
import com.fadlurahmanf.starter_app_mvp.ui.core.dialog.TransparentDialog
import com.google.android.material.snackbar.Snackbar

typealias InflateLayoutActivity<T> = (LayoutInflater) -> T

abstract class BaseActivity<VB:ViewBinding>(
    var inflate:InflateLayoutActivity<VB>
):AppCompatActivity(), BaseView, ConnectivityReceiver.ConnectivityListener {

    private lateinit var _appComponent:ApplicationComponent
    val appComponent get() = _appComponent

    private var _binding:VB ?= null
    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        initComponent()
        injectView()
        super.onCreate(savedInstanceState)
        setLayout()
        internalSetup()
        setup()
    }

    private fun initComponent(){
        _appComponent = (applicationContext as BaseApp).appComponent
    }

    override fun onStart() {
        super.onStart()
        startConnectivityReceiver()
    }

    open fun setScreenStyle(color:Int = R.color.white, isLight:Boolean = true, isFullScreen:Boolean=false){
        if (isFullScreen){
            WindowCompat.setDecorFitsSystemWindows(window, false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = Color.TRANSPARENT
            }
            val window = this.window
            val decorView = window.decorView
            val wic = WindowInsetsControllerCompat(window, decorView)
            wic.isAppearanceLightStatusBars = isLight
        }

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = this.window
            val decorView = window.decorView
            val wic = WindowInsetsControllerCompat(window, decorView)
            wic.isAppearanceLightStatusBars = isLight
            window.statusBarColor = ContextCompat.getColor(this, color)
        }*/
    }

    fun setTransparentStatusBar(){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun onResume() {
        super.onResume()
        startConnectivityReceiver()
    }

    override fun onStop() {
        super.onStop()
        if (connectivityReceiver!=null){
            unregisterReceiver(connectivityReceiver)
            connectivityReceiver = null
        }
    }

    override fun onPause() {
        super.onPause()
        if (connectivityReceiver!=null){
            unregisterReceiver(connectivityReceiver)
            connectivityReceiver = null
        }
    }

    abstract fun injectView()

    private fun setLayout(){
        _binding = inflate.invoke(layoutInflater)
        setContentView(binding?.root)
    }

    abstract fun setup()

    open fun internalSetup(){}

    override fun errorScreen(message: String?) {
        showSnackBar(message)
    }

    override fun errorConnection() {}

    private var connectivityReceiver:ConnectivityReceiver ?= null

    open fun startConnectivityReceiver(){
        if (connectivityReceiver==null){
            connectivityReceiver = ConnectivityReceiver()
            connectivityReceiver?.connectivityListener = this
            @Suppress("DEPRECATION")
            registerReceiver(connectivityReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (connectivityReceiver != null){
            if (!isConnected){
                showSnackBar("You are going offline", Snackbar.LENGTH_LONG)
            }
        }
    }

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
            okDialog?.show(supportFragmentManager, OkDialog::class.java.simpleName)
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
            confirmDialog?.show(supportFragmentManager, ConfirmDialog::class.java.simpleName)
        }
    }

    fun dismissConfirmDialog(){
        if (confirmDialog != null){
            confirmDialog?.dismiss()
            confirmDialog = null
        }
    }

    open fun showSnackBar(message: String?, typeLong:Int?=null){
        Snackbar.make(binding!!.root, message?:"", typeLong?:Snackbar.LENGTH_LONG).show()
    }

    override fun forceRestart(message: String?) {
        showOkDialog(
            title = "Oops..",
            content = message?:"",
            okListener = {
                dismissOkDialog()
                val intent = Intent(this, SplashActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        )
    }

    private var loadingDialog:LoadingDialog ?= null
    override fun loadingDialog(loadingText:String?, isCancelable:Boolean) {
        if (loadingDialog == null){
            loadingDialog = LoadingDialog()
            var bundle = Bundle()
            bundle.putString(LoadingDialog.TEXT, loadingText)
            bundle.putBoolean(LoadingDialog.IS_CANCELABLE, isCancelable)
            loadingDialog?.arguments = bundle
            loadingDialog?.show(supportFragmentManager, LoadingDialog::class.java.simpleName)
        }
    }

    override fun dismissLoadingDialog() {
        if (loadingDialog != null){
            loadingDialog?.dismiss()
            loadingDialog = null
        }
    }

    private var transparentDialog:TransparentDialog ?= null
    fun showTransparentDialog(
        isCancelable: Boolean = false
    ){
        if (transparentDialog == null){
            var bundle = Bundle()
            transparentDialog = TransparentDialog()
            bundle.putBoolean(TransparentDialog.IS_CANCELABLE, isCancelable)
            transparentDialog?.arguments = bundle
            transparentDialog?.show(supportFragmentManager, TransparentDialog::class.java.simpleName)
        }
    }

    fun dismissTransparentDialog(){
        if (transparentDialog != null){
            transparentDialog?.dismiss()
            transparentDialog = null
        }
    }
}