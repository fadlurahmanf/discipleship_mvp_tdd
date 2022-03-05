package com.fadlurahmanf.starter_app_mvp.data.repository.core

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.base.BasePreference
import com.fadlurahmanf.starter_app_mvp.core.constant.ParamsKeySP
import com.fadlurahmanf.starter_app_mvp.data.response.auth.LoginResponse
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyGroupResponse
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(context: Context):BasePreference(context) {
    var bearerToken:String ?= null
    get() {
        return getString(ParamsKeySP.BEARER_TOKEN)
    }set(value) {
        if (value == null){
            clearData(ParamsKeySP.BEARER_TOKEN)
            field = null
        }else{
            saveString(ParamsKeySP.BEARER_TOKEN, "Bearer $value")
            field = "Bearer $value"
        }
    }

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

    var myGroup:List<MyGroupResponse> ?= null
    get() {
        return getListData(ParamsKeySP.MY_GROUP, MyGroupResponse::class.java)
    }set(value) {
        if (value == null){
            clearData(ParamsKeySP.MY_GROUP)
            field = null
        }else{
            saveListData(ParamsKeySP.MY_GROUP, value)
            field = value
        }
    }

    var mySubscription:List<LoginResponse.User.Subscription> ?= null
    get() {
        return getListData(ParamsKeySP.MY_SUBSCRIPTION, LoginResponse.User.Subscription::class.java)
    }set(value) {
        if (value == null){
            clearData(ParamsKeySP.MY_SUBSCRIPTION)
            field = null
        }else{
            saveListData(ParamsKeySP.MY_SUBSCRIPTION, value)
            field = value
        }
    }
}