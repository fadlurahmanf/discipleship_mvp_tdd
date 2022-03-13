package com.fadlurahmanf.starter_app_mvp.data.api.core

import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.TestimonialResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface TestimonialApi {
    @GET("api/testimonial/all")
    fun getTestimonial() : Observable<BaseResponse<List<TestimonialResponse>>>

    @GET("api/testimonial/all")
    fun getTestimonialCall() : Call<BaseResponse<List<TestimonialResponse>>>
}