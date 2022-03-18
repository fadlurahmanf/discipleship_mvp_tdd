package com.fadlurahmanf.starter_app_mvp.core.services

import android.content.Context
import androidx.work.*
import androidx.work.WorkerFactory
import androidx.work.rxjava3.RxWorker
import com.fadlurahmanf.starter_app_mvp.core.utils.RetrofitService
import com.fadlurahmanf.starter_app_mvp.core.utils.TestimonialApiService
import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

interface ChildWorkerFactory{
    fun create(context: Context, workerParams: WorkerParameters):ListenableWorker
}

@Singleton
class AppWorkerFactory @Inject constructor(
    private var workerFactories:Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<ChildWorkerFactory>>
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val provider = workerFactories.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }
        val providerValue = provider?.value?.get() ?: throw IllegalArgumentException("Unknown name class $workerClassName")
        return providerValue.create(appContext, workerParameters)
    }
}

class ExampleWorkManager (
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams){

    private var services:TestimonialApiService = RetrofitService.services(context).create(TestimonialApiService::class.java)

    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            var result = services.getTestimonial()
            println("MASUK HASIL ${result.message}")
            return@withContext Result.success()
        }
        return Result.failure()
    }
}

class ExampleRxWorker(
    context: Context,
    workerParams: WorkerParameters
) : RxWorker(context, workerParams){

    private var services:TestimonialApiService = RetrofitService.services(context).create(TestimonialApiService::class.java)

    override fun createWork(): Single<Result> {
        return Single.fromObservable(services.getTestimonialObservable().map {
            if (it.code == 100){
                Result.success(workDataOf("MESSAGE" to Gson().toJson(it)))
            }else{
                Result.failure(workDataOf("MESSAGE" to Gson().toJson(BaseResponse(code = it.code, message = it.message, data = null))))
            }
        }.onErrorReturn {
            Result.failure(workDataOf("MESSAGE" to Gson().toJson(BaseResponse(code = null, message = it.message, data = null))))
        })
//        return services.getTestimonialObservable()
//            .toList()
//            .map {
//                var result = it.first()
//                if (result.code == 100){
//                    Result.success(workDataOf("MESSAGE" to (Gson().toJson(result))))
//                }else{
//                    Result.failure(workDataOf("MESSAGE" to (result.message?:"")))
//                }
//            }
    }

}