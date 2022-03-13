package com.fadlurahmanf.starter_app_mvp.data.entity.auth

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.base.network.AbstractNetwork
import com.fadlurahmanf.starter_app_mvp.data.api.auth.UserApi
import com.fadlurahmanf.starter_app_mvp.data.body.auth.LoginBody
import com.fadlurahmanf.starter_app_mvp.data.body.auth.RegisterBody
import javax.inject.Inject

class UserEntity @Inject constructor(
    var context: Context
) : AbstractNetwork<UserApi>(context) {
    override fun getApi(): Class<UserApi> {
        return UserApi::class.java
    }

    fun login(body: LoginBody) = networkService(30).login(body = body)

    fun regis(body: RegisterBody) = networkService(30).regis(body = body)
}