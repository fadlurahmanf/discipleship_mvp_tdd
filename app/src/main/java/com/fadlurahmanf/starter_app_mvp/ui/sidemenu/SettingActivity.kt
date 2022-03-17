package com.fadlurahmanf.starter_app_mvp.ui.sidemenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.base.BaseActivity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AppRepository
import com.fadlurahmanf.starter_app_mvp.databinding.ActivitySettingBinding
import com.fadlurahmanf.starter_app_mvp.di.component.SideMenuComponent
import com.fadlurahmanf.starter_app_mvp.ui.sidemenu.language.SelectLanguageActivity
import javax.inject.Inject

class SettingActivity : BaseActivity<ActivitySettingBinding>(ActivitySettingBinding::inflate) {
    private lateinit var component: SideMenuComponent
    override fun injectView() {
        component = appComponent.sideMenuComponent().create()
        component.inject(this)
    }

    override fun setup() {
        supportActionBar?.hide()
        setScreenStyle(isFullScreen = true)
        initView()
        initAction()
    }

    private fun initAction() {
        binding?.layoutLanguage?.clSetting?.setOnClickListener {
            val intent = Intent(this, SelectLanguageActivity::class.java)
            startActivity(intent)
        }
    }

    @Inject
    lateinit var appRepository:AppRepository

    private fun initView() {
        //language
        var language = binding?.layoutLanguage
        language?.clSetting?.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        language?.tvSettingTitle?.text = "Language"
        language?.tvDescription?.text = appRepository.paramsLanguage?.name
    }

}