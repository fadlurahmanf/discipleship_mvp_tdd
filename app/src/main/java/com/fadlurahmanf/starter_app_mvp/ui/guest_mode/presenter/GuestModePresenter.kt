package com.fadlurahmanf.starter_app_mvp.ui.guest_mode.presenter

import com.fadlurahmanf.starter_app_mvp.base.BasePresenter
import com.fadlurahmanf.starter_app_mvp.core.extension.uiSubscribe
import com.fadlurahmanf.starter_app_mvp.data.entity.core.TestimonialEntity
import javax.inject.Inject

class GuestModePresenter @Inject constructor(
    var testimonialEntity: TestimonialEntity
):BasePresenter<GuestModeContract.View>(), GuestModeContract.Presenter {
    override fun getTestimonial() {
        view?.testimonialLoading()
        addSubscription(
            testimonialEntity.getTestimonial().uiSubscribe(
                {
                    if (it.code == 100){
                        view?.testimonialLoaded(list = it.data?: listOf())
                    }else{
                        view?.testimonialFailed(it.message)
                    }
                },
                {
                    view?.testimonialFailed(it.message)
                },
                {}
            )
        )
    }
}