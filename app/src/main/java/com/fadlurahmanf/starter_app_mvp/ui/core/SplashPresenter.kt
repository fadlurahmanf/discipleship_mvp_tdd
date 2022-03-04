package com.fadlurahmanf.starter_app_mvp.ui.core

import com.fadlurahmanf.starter_app_mvp.BuildConfig
import com.fadlurahmanf.starter_app_mvp.base.BasePresenter
import com.fadlurahmanf.starter_app_mvp.core.extension.uiSubscribe
import com.fadlurahmanf.starter_app_mvp.data.entity.core.CheckUpdateEntity
import com.fadlurahmanf.starter_app_mvp.data.entity.core.LanguageEntity
import com.fadlurahmanf.starter_app_mvp.data.model.core.CheckUpdateBody
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AppRepository
import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.CheckUpdateResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.LanguageResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    var checkUpdateEntity: CheckUpdateEntity,
    var languageEntity: LanguageEntity,
    var appRepository: AppRepository
):BasePresenter<SplashContract.View>(), SplashContract.Presenter {

    data class UpdateLanguageResponse(
        var updateResponse:BaseResponse<CheckUpdateResponse>?,
        var languageResponse:BaseResponse<LanguageResponse>?
    )

    override fun checkUpdateLanguage() {
        var body: CheckUpdateBody = CheckUpdateBody(
            type = "app", version = BuildConfig.VERSION_NAME, os = "android"
        )
        if (appRepository.paramsLanguage != null && appRepository.languageResponse != null){
            addSubscription(
                checkUpdateEntity.checkUpdate(body).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            if (it.code == 100){
                                view?.checkUpdateLanguageSuccess()
                            }else{
                                view?.checkUpdateLanguageFailed(message = it.message)
                            }
                        },
                        {
                            view?.checkUpdateLanguageFailed(message = it.message)
                        },
                        {}
                    )
            )
        }else{
            addSubscription(Observable.zip(
                checkUpdateEntity.checkUpdate(body).onErrorReturn {
                    BaseResponse<CheckUpdateResponse>(message = it.message)
                },
                languageEntity.getLanguage("en").onErrorReturn {
                    BaseResponse<LanguageResponse>()
                },
                BiFunction { t1, t2 ->  UpdateLanguageResponse(t1, t2)}
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it.updateResponse?.code == 100 && it.languageResponse?.code == 100){
                            appRepository.paramsLanguage = "en"
                            appRepository.languageResponse = it.languageResponse!!.data
                            view?.checkUpdateLanguageSuccess()
                        }else if (it.updateResponse?.code == 100 && it.languageResponse?.code!=100){
                            view?.forceRestart(it.languageResponse?.message)
                        }else if (it.updateResponse?.code != 100 && it.languageResponse?.code == 100){
                            appRepository.paramsLanguage = "en"
                            appRepository.languageResponse = it.languageResponse!!.data
                            view?.checkUpdateLanguageFailed(message = it.updateResponse?.message)
                        }else{
                            view?.checkUpdateLanguageFailed(message = it.languageResponse?.message?:it.updateResponse?.message?:"")
                        }
                    },
                    {
                        view?.checkUpdateLanguageFailed(message = it.message)
                    },
                    {}
                )
            )
        }
    }
}