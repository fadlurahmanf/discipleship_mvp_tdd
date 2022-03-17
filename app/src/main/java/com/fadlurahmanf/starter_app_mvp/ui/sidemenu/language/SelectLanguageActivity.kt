package com.fadlurahmanf.starter_app_mvp.ui.sidemenu.language

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fadlurahmanf.starter_app_mvp.base.BaseActivity
import com.fadlurahmanf.starter_app_mvp.base.BaseMvpActivity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AppRepository
import com.fadlurahmanf.starter_app_mvp.data.response.core.ParameterLanguageResponse
import com.fadlurahmanf.starter_app_mvp.databinding.ActivitySelectLanguageBinding
import com.fadlurahmanf.starter_app_mvp.di.component.SideMenuComponent
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.language.adapter.ParameterLanguageAdapter
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.language.presenter.SelectLanguageContract
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.language.presenter.SelectLanguagePresenter
import javax.inject.Inject

class SelectLanguageActivity : BaseMvpActivity<SelectLanguagePresenter, ActivitySelectLanguageBinding>(ActivitySelectLanguageBinding::inflate),
    SelectLanguageContract.View, ParameterLanguageAdapter.CallBack {
    private lateinit var component: SideMenuComponent
    override fun injectView() {
        component = appComponent.sideMenuComponent().create()
        component.inject(this)
    }

    override fun setup() {
        supportActionBar?.hide()
        setScreenStyle(isFullScreen = true)
        initAdapter()
        presenter.getParameterLanguage()
    }

    private var listParameterLanguage:ArrayList<ParameterLanguageResponse> = arrayListOf()
    private var selectedParams:ArrayList<String> = arrayListOf<String>()
    private lateinit var adapter:ParameterLanguageAdapter

    @Inject
    lateinit var appRepository: AppRepository

    private fun initAdapter() {
        selectedParams.clear()
        selectedParams.add(appRepository.paramsLanguage?.code!!)
        adapter = ParameterLanguageAdapter(listParameterLanguage, selectedParams)
        adapter.setOnItemCallBack(this)
        binding?.rvParamLanguage?.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    @Inject
    lateinit var presenter: SelectLanguagePresenter

    override fun initPresenterView() {
        presenter.view = this
    }

    override fun getParameterLanguageLoading() {
        binding?.rvParamLanguage?.visibility = View.GONE
        binding?.progress?.visibility = View.VISIBLE
    }

    override fun getParameterSuccess(listParameterLanguage: List<ParameterLanguageResponse>) {
        binding?.rvParamLanguage?.visibility = View.VISIBLE
        binding?.progress?.visibility = View.GONE
        this.listParameterLanguage.clear()
        this.listParameterLanguage.addAll(listParameterLanguage)
//        this.listParameterLanguage.clear()
//        this.listParameterLanguage.add(
//            ParameterLanguageResponse(
//                code = "ENGLISH", name = "ENGLISH"
//            )
//        )
//        this.listParameterLanguage.add(
//            ParameterLanguageResponse(
//                code = "INDONESIA", name = "INDONESIA"
//            )
//        )
//        this.listParameterLanguage.add(
//            ParameterLanguageResponse(
//                code = "CHINESE", name = "CHINESE"
//            )
//        )
        this.adapter.notifyDataSetChanged()
    }

    override fun getParameterFailed(message:String?) {
        showSnackBar(message)
        binding?.rvParamLanguage?.visibility = View.VISIBLE
        binding?.progress?.visibility = View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onParameterLanguageClicked(param: ParameterLanguageResponse) {
        presenter.selectLanguage(param)
    }

    override fun selectLanguageLoading() {
        showTransparentDialog()
    }

    override fun selectLanguageSuccess(param: ParameterLanguageResponse) {
        dismissTransparentDialog()
        this.selectedParams.clear()
        this.selectedParams.add(param.code!!)
        selectedParams.forEach {
            println("ON PARAMTER LANGUAGE CLICK ${it}")
        }
        this.adapter.notifyDataSetChanged()
    }

    override fun selectLanguageFailed(message: String?) {
        dismissTransparentDialog()
        showSnackBar(message)
    }
}