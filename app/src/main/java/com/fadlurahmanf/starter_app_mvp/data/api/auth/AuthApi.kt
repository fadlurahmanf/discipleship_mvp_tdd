package com.fadlurahmanf.starter_app_mvp.data.api.auth

import androidx.annotation.Nullable
import com.fadlurahmanf.starter_app_mvp.data.body.auth.LoginBody
import com.fadlurahmanf.starter_app_mvp.data.body.auth.RegisterBody
import com.fadlurahmanf.starter_app_mvp.data.response.auth.LoginResponse
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyGroupResponse
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyTrainingResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface AuthApi {
    @POST("api/user/auth/login")
    fun login(@Body body: LoginBody) : Observable<BaseResponse<LoginResponse>>

    @POST("api/user/auth/register")
    fun regis(@Body body:RegisterBody) : Observable<BaseResponse<Nullable>>

    @GET("api/user/subscription/all")
    fun getMySubscriptions(
        @Header("Authorization") authorization:String
    ) : Observable<BaseResponse<List<LoginResponse.User.Subscription>>>

    @GET("api/study-group/my")
    fun getMyGroup(
        @Header("Authorization") authorization: String
    ) : Observable<BaseResponse<List<MyGroupResponse>>>

    @GET("api/training/study/all")
    fun getMyTrainingStudyTopic(
        @Header("Authorization") authorization: String
    ) : Observable<BaseResponse<List<MyTrainingResponse>>>
}