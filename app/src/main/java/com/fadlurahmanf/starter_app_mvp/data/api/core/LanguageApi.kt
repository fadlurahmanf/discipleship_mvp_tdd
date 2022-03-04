package com.fadlurahmanf.starter_app_mvp.data.api.core

import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.LanguageResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface LanguageApi {
    @GET("api/translation/all")
    fun getLanguage(
        @Query("lang") lang:String
    ) : Observable<BaseResponse<LanguageResponse>>
}