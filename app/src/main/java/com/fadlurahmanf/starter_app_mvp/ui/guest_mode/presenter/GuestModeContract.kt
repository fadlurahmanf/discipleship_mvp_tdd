package com.fadlurahmanf.starter_app_mvp.ui.guest_mode.presenter

import com.fadlurahmanf.starter_app_mvp.base.BaseView
import com.fadlurahmanf.starter_app_mvp.data.response.core.TestimonialResponse

interface GuestModeContract {
    interface View:BaseView{
        fun testimonialLoading()
        fun testimonialLoaded(list:List<TestimonialResponse>)
        fun testimonialFailed(message:String?)
    }
    interface Presenter{
        fun getTestimonial()
    }
}