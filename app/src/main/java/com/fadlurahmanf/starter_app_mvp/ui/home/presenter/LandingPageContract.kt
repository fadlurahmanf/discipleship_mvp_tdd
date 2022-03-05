package com.fadlurahmanf.starter_app_mvp.ui.home.presenter

import com.fadlurahmanf.starter_app_mvp.base.BaseView
import com.fadlurahmanf.starter_app_mvp.data.response.auth.LoginResponse
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyGroupResponse
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyTrainingResponse

interface LandingPageContract {
    interface View:BaseView{
        fun myGroupAndSubscriptionLoading()
        fun myGroupAndMySubscriptionLoaded(myGroups: List<MyGroupResponse>, mySubscriptions:List<LoginResponse.User.Subscription>)
        fun myGroupAndMySubscriptionError(message:String?)

        fun getTrainingLoading()
        fun getTrainingLoaded(list: List<MyTrainingResponse>)
        fun myTrainingFailed(message: String?)
    }
    interface Presenter{
        fun getMyGroupAndMySubscription()
        fun getMyTraining()
    }
}