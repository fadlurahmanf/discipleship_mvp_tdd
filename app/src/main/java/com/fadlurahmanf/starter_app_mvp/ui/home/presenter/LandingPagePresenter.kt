package com.fadlurahmanf.starter_app_mvp.ui.home.presenter

import com.fadlurahmanf.starter_app_mvp.base.BasePresenter
import com.fadlurahmanf.starter_app_mvp.core.extension.uiSubscribe
import com.fadlurahmanf.starter_app_mvp.data.entity.auth.UserWithAuthEntity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import com.fadlurahmanf.starter_app_mvp.data.response.auth.LoginResponse
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyGroupResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import javax.inject.Inject

class LandingPagePresenter @Inject constructor(
    var userWithAuthEntity: UserWithAuthEntity,
    var authRepository: AuthRepository
) : BasePresenter<LandingPageContract.View>(), LandingPageContract.Presenter {
    data class PairGroupSubscriptionResponse(
        var group:BaseResponse<List<MyGroupResponse>> ?= null,
        var subscription:BaseResponse<List<LoginResponse.User.Subscription>> ?= null,
    )

    override fun getMyGroupAndMySubscription() {
        view?.myGroupAndSubscriptionLoading()
        addSubscription(
            Observable.zip(
                userWithAuthEntity.getMyGroups(),
                userWithAuthEntity.getSubscription(),
                BiFunction { t1, t2 -> PairGroupSubscriptionResponse(t1, t2) }
            ).uiSubscribe(
                {
                    if (it.group?.code == 100 && it.group?.data != null
                        && it.subscription?.code == 100 && it.subscription?.data != null){
                        authRepository.myGroup = it.group?.data
                        authRepository.mySubscription = it.subscription?.data
                        view?.myGroupAndMySubscriptionLoaded(it.group!!.data!!, it.subscription!!.data!!)
                    }else if (it.group?.code != 100 || it.group?.data == null){
                        view?.myGroupAndMySubscriptionError(it.group?.message)
                    }else if (it.subscription?.code != 100 || it.subscription?.data == null){
                        view?.myGroupAndMySubscriptionError(it.subscription?.message)
                    }else{
                        view?.myGroupAndMySubscriptionError(it.group?.message?:it.subscription?.message)
                    }
                },
                {
                    view?.myGroupAndMySubscriptionError(it.message)
                },
                {}
            )
        )
    }

    override fun getMyTraining() {
        view?.getTrainingLoading()
        addSubscription(
            userWithAuthEntity.getMyTrainingStudyTopic()
                .uiSubscribe(
                    {
                        if (it.code == 100 && it.data != null){
                            view?.getTrainingLoaded(it.data!!)
                        }else{
                            view?.myTrainingFailed(it.message)
                        }
                    },
                    {
                        view?.myTrainingFailed(it.message)
                    },
                    {}
                )
        )
    }
}