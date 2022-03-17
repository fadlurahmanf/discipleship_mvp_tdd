package com.fadlurahmanf.starter_app_mvp.data.repository.core

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.base.BasePreference
import com.fadlurahmanf.starter_app_mvp.core.constant.ParamsKeySP
import com.fadlurahmanf.starter_app_mvp.data.response.core.LanguageResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.ParameterLanguageResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    context: Context
) : BasePreference(context) {
    var paramsLanguage:ParameterLanguageResponse ?= null
    get() {
        return getData(ParamsKeySP.APP_PARAMS_LANGUAGE, ParameterLanguageResponse::class.java)
    }set(value) {
        if (value == null){
            clearData(ParamsKeySP.APP_PARAMS_LANGUAGE)
            field = null
        }else{
            saveData(ParamsKeySP.APP_PARAMS_LANGUAGE, value)
            field = value
        }
    }

    var languageResponse: LanguageResponse?= null
        get() {
            field = getData(ParamsKeySP.LANGUAGE_RESPONSE, LanguageResponse::class.java)
            return field
        }set(value) {
        if (value == null){
            clearData(ParamsKeySP.LANGUAGE_RESPONSE)
            field = null
        }else{
            saveData(ParamsKeySP.LANGUAGE_RESPONSE, value)
            field = value
        }
    }
}