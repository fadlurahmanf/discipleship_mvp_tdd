package com.fadlurahmanf.starter_app_mvp.data.authenticator

import com.fadlurahmanf.starter_app_mvp.data.entity.core.TestimonialEntity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.TestimonialResponse
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.lang.Exception
import javax.inject.Inject


class TokenInterceptor @Inject constructor(
    var testimonialEntity:TestimonialEntity,
    var authRepository: AuthRepository
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())
        try {
            var jsonResponse = JSONObject(response.peekBody(Long.MAX_VALUE).string())
            if (jsonResponse.optString("message") == "Not authenticated"){
                return runBlocking {
                   if (hitsSuspend() != null){
                       println("MASUK ${hitsSuspend()?.message}")
                       authRepository.bearerToken = hitsSuspend()?.message
                       chain.proceed(chain.request().newBuilder().header("Authorization", "Bearer ${hitsSuspend()?.message}").build())
                   }else{
                       chain.proceed(chain.request())
                   }
                }
            }else{
                return chain.proceed(chain.request())
            }
        }catch (e:Exception){
            println("MASUK ERROR ${e.message}")
            return chain.proceed(chain.request())
        }
    }

    private fun hitsSuspend(): BaseResponse<List<TestimonialResponse>>?{
        var testimonial = testimonialEntity.getTestimonialCall().execute()
        testimonial.body()?.message = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRmZmFqYXJpQGdtYWlsLmNvbSIsInN1YiI6MjIsInJvbGUiOiJwYXJ0aWNpcGFudCIsImlhdCI6MTY0NjQ1ODczOH0.APr6QtCXFfI2lozVSdFVf43pjs5SgJteMxPJmdSRJj8"
        return testimonial.body()
//        return BaseResponse<List<TestimonialResponse>>(message = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRmZmFqYXJpQGdtYWlsLmNvbSIsInN1YiI6MjIsInJvbGUiOiJwYXJ0aWNpcGFudCIsImlhdCI6MTY0NjQ1ODczOH0.APr6QtCXFfI2lozVSdFVf43pjs5SgJteMxPJmdSRJj8")
    }
}