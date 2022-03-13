package com.fadlurahmanf.starter_app_mvp.data.entity.core

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.base.network.AbstractNetwork
import com.fadlurahmanf.starter_app_mvp.data.api.core.TestimonialApi
import javax.inject.Inject

class TestimonialEntity @Inject constructor(
    var context: Context
) : AbstractNetwork<TestimonialApi>(context){
    override fun getApi(): Class<TestimonialApi> {
        return TestimonialApi::class.java
    }

    fun getTestimonial() = networkService(30).getTestimonial()

    fun getTestimonialCall() = networkService(30).getTestimonialCall()
}