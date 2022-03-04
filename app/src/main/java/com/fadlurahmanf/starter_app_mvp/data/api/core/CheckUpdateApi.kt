package com.fadlurahmanf.starter_app_mvp.data.api.core

import com.fadlurahmanf.starter_app_mvp.data.model.core.CheckUpdateBody
import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.CheckUpdateResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckUpdateApi {
    @POST("api/check-update")
    fun checkUpdate(@Body body: CheckUpdateBody) : Observable<BaseResponse<CheckUpdateResponse>>
}