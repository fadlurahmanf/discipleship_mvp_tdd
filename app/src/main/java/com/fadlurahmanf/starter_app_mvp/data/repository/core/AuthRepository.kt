package com.fadlurahmanf.starter_app_mvp.data.repository.core

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.base.BasePreference
import com.fadlurahmanf.starter_app_mvp.core.constant.ParamsKeySP
import com.fadlurahmanf.starter_app_mvp.data.response.auth.LoginResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(context: Context):BasePreference(context) {
    var password:String ?= null
    get() {
        return getString(ParamsKeySP.PASSWORD_USER)
    }set(value) {
        if (value == null){
            clearData(ParamsKeySP.PASSWORD_USER)
            field = null
        }else{
            saveString(ParamsKeySP.PASSWORD_USER, value)
            field = value
        }
    }

    var loginResponse:LoginResponse ?= null
    get() {
        return getData(ParamsKeySP.LOGIN_RESPONSE, LoginResponse::class.java)
    }set(value) {
        if (value == null){
            clearData(ParamsKeySP.LOGIN_RESPONSE)
            field = null
        }else{
            saveData(ParamsKeySP.LOGIN_RESPONSE, value)
            field = value
        }
    }
}