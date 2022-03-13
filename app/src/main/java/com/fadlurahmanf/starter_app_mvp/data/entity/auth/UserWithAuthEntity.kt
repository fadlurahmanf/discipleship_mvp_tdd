package com.fadlurahmanf.starter_app_mvp.data.entity.auth

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.base.network.AuthAbstractNetwork
import com.fadlurahmanf.starter_app_mvp.data.api.auth.UserApi
import com.fadlurahmanf.starter_app_mvp.data.body.auth.LoginBody
import com.fadlurahmanf.starter_app_mvp.data.body.auth.RegisterBody
import com.fadlurahmanf.starter_app_mvp.data.entity.core.TestimonialEntity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import javax.inject.Inject

class UserWithAuthEntity @Inject constructor(
    var context: Context,
    var authRepository: AuthRepository,
    var testimonialEntity: TestimonialEntity
) : AuthAbstractNetwork<UserApi>(authRepository,context, testimonialEntity) {
    override fun getApi(): Class<UserApi> {
        return UserApi::class.java
    }

    fun getSubscription() = networkService(30).getMySubscriptions()

    fun getMyGroups() = networkService(30).getMyGroup()

    fun getMyTrainingStudyTopic() = networkService(30).getMyTrainingStudyTopic()
}