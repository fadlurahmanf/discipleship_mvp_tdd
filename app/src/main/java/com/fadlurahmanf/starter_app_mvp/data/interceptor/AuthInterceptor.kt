package com.fadlurahmanf.starter_app_mvp.data.interceptor

import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    var authRepository: AuthRepository
):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(addHeader(chain.request()))
    }

    private fun addHeader(request: Request):Request{
        return request.newBuilder()
            .addHeader("Authorization", authRepository.bearerToken!!)
            .build()
    }
}