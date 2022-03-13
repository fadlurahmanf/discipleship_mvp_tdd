package com.fadlurahmanf.starter_app_mvp.base.network

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.data.authenticator.TokenInterceptor
import com.fadlurahmanf.starter_app_mvp.data.entity.core.TestimonialEntity
import com.fadlurahmanf.starter_app_mvp.data.interceptor.AuthInterceptor
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import okhttp3.OkHttpClient

abstract class AuthAbstractNetwork<T>(
    private var authRepository: AuthRepository,
    private var context: Context,
    private var testimonialEntity: TestimonialEntity,
):AbstractNetwork<T>(context) {

    override fun okHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.addInterceptor(AuthInterceptor(authRepository))
//            .addInterceptor(TokenInterceptor(testimonialEntity, authRepository))
        return super.okHttpClientBuilder(builder)
    }
}