package com.fadlurahmanf.starter_app_mvp.data.entity.core

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.base.network.AbstractNetwork
import com.fadlurahmanf.starter_app_mvp.data.api.core.LanguageApi
import javax.inject.Inject

class LanguageEntity @Inject constructor(
    var context: Context
):AbstractNetwork<LanguageApi>(context) {
    override fun getApi(): Class<LanguageApi> {
        return LanguageApi::class.java
    }

    fun getLanguage(lang:String) = networkService(30).getLanguage(lang)

    fun getParameterLanguage() = networkService(30).getAllParameterLanguage()
}