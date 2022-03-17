package com.fadlurahmanf.starter_app_mvp.ui.core.presenter

import com.fadlurahmanf.starter_app_mvp.BuildConfig
import com.fadlurahmanf.starter_app_mvp.base.BasePresenter
import com.fadlurahmanf.starter_app_mvp.data.entity.core.CheckUpdateEntity
import com.fadlurahmanf.starter_app_mvp.data.entity.core.LanguageEntity
import com.fadlurahmanf.starter_app_mvp.data.model.core.CheckUpdateBody
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AppRepository
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.CheckUpdateResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.LanguageResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.ParameterLanguageResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    var checkUpdateEntity: CheckUpdateEntity,
    var languageEntity: LanguageEntity,
    var appRepository: AppRepository,
    var authRepository: AuthRepository
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
                            if (it.code == 100 && it.data != null){
                                if (authRepository.isLoggedIn == true){
                                    view?.goToLandingPage(it.data!!)
                                }else{
                                    view?.goToGuestMode(it.data!!)
                                }
                            }else{
                                if (authRepository.isLoggedIn == true){
                                    view?.goToLandingPage(CheckUpdateResponse())
                                }else{
                                    view?.goToGuestMode(CheckUpdateResponse())
                                }
                            }
                        },
                        {
                            if (authRepository.isLoggedIn == true){
                                view?.goToLandingPage(CheckUpdateResponse())
                            }else{
                                view?.goToGuestMode(CheckUpdateResponse())
                            }
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
                    BaseResponse<LanguageResponse>(message = it.message)
                },
                BiFunction { t1, t2 ->  UpdateLanguageResponse(t1, t2) }
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it.updateResponse?.code == 100 && it.languageResponse?.code == 100 && it.languageResponse!!.data != null && it.updateResponse!!.data != null){
                            appRepository.paramsLanguage = ParameterLanguageResponse(
                                name = "English", code = "en"
                            )
                            appRepository.languageResponse = it.languageResponse!!.data
                            if (authRepository.isLoggedIn == true){
                                view?.goToLandingPage(it.updateResponse!!.data!!)
                            }else{
                                view?.goToGuestMode(it.updateResponse!!.data!!)
                            }
                        }else if (it.updateResponse?.code == 100 && it.languageResponse?.code!=100){
                            view?.forceRestart(it.languageResponse?.message)
                        }else if (it.updateResponse?.code != 100 && it.languageResponse?.code == 100){
                            appRepository.paramsLanguage = ParameterLanguageResponse(
                                name = "English", code = "en"
                            )
                            appRepository.languageResponse = it.languageResponse!!.data
                            view?.goToGuestMode(CheckUpdateResponse())
                        }else{
                            view?.goToGuestMode(CheckUpdateResponse())
                        }
                    },
                    {
                        view?.forceRestart(it.message)
                    },
                    {}
                )
            )
        }
    }
}