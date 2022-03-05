package com.fadlurahmanf.starter_app_mvp.data.entity.auth

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.base.network.AbstractNetwork
import com.fadlurahmanf.starter_app_mvp.data.api.auth.AuthApi
import com.fadlurahmanf.starter_app_mvp.data.body.auth.LoginBody
import com.fadlurahmanf.starter_app_mvp.data.body.auth.RegisterBody
import retrofit2.http.Header
import javax.inject.Inject

class AuthEntity @Inject constructor(
    var context: Context
) : AbstractNetwork<AuthApi>(context) {
    override fun getApi(): Class<AuthApi> {
        return AuthApi::class.java
    }

    fun login(body: LoginBody) = networkService(30).login(body = body)

    fun regis(body: RegisterBody) = networkService(30).regis(body = body)

    fun getSubscription(authorization:String) = networkService(30).getMySubscriptions(authorization)

    fun getMyGroups(authorization: String) = networkService(30).getMyGroup(authorization)

    fun getMyTrainingStudyTopic(authorization: String) = networkService(30).getMyTrainingStudyTopic(authorization)
}