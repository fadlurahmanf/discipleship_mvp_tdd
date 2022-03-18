package com.fadlurahmanf.starter_app_mvp.ui.sidemenu.language.presenter

import com.fadlurahmanf.starter_app_mvp.base.BasePresenter
import com.fadlurahmanf.starter_app_mvp.core.event.ChangeText
import com.fadlurahmanf.starter_app_mvp.core.extension.uiSubscribe
import com.fadlurahmanf.starter_app_mvp.core.utils.RxBus
import com.fadlurahmanf.starter_app_mvp.data.entity.core.LanguageEntity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AppRepository
import com.fadlurahmanf.starter_app_mvp.data.response.core.ParameterLanguageResponse
import javax.inject.Inject

class SelectLanguagePresenter @Inject constructor(
    var languageEntity: LanguageEntity,
    var appRepository: AppRepository
) : BasePresenter<SelectLanguageContract.View>(), SelectLanguageContract.Presenter{
    override fun getParameterLanguage() {
        view?.getParameterLanguageLoading()
        addSubscription(languageEntity.getParameterLanguage().uiSubscribe(
            {
               if (it.code == 100){
                   view?.getParameterSuccess(it.data!!)
               }else{
                   view?.getParameterFailed(message = it.message)
               }
            },
            {
                view?.getParameterFailed(it.message)
            },
            {}
        ))
    }

    override fun selectLanguage(param:ParameterLanguageResponse) {
        view?.selectLanguageLoading()
        addSubscription(languageEntity.getLanguage(param.code!!).uiSubscribe(
            {
                if (it.code == 100){
                    appRepository.paramsLanguage = param
                    appRepository.languageResponse = it.data
                    RxBus.publish(ChangeText())
                    view?.selectLanguageSuccess(param)
                }else{
                    view?.selectLanguageFailed(it.message)
                }
            },
            {
                view?.selectLanguageFailed(it.message)
            }
        ))
    }
}