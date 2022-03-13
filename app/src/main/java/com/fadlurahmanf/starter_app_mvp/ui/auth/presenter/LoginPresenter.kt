package com.fadlurahmanf.starter_app_mvp.ui.auth.presenter

import com.fadlurahmanf.starter_app_mvp.base.BasePresenter
import com.fadlurahmanf.starter_app_mvp.core.extension.uiSubscribe
import com.fadlurahmanf.starter_app_mvp.data.body.auth.LoginBody
import com.fadlurahmanf.starter_app_mvp.data.entity.auth.UserEntity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    var userEntity: UserEntity,
    var authRepository: AuthRepository
) : BasePresenter<LoginContract.View>(), LoginContract.Presenter{

    override fun login(email: String, password: String) {
        view?.loadingDialog()
        addSubscription(
            userEntity.login(LoginBody(email, password))
                .uiSubscribe(
                    {
                        view?.dismissLoadingDialog()
                        if (it.code == 100 && it.data != null){
                            authRepository.password = password
                            authRepository.bearerToken = it.data?.accessToken
                            authRepository.loginResponse = it.data
                            view?.loginSuccess(it.data!!)
                        }else{
                            view?.loginFailed(it.message)
                        }
                    },
                    {
                        view?.loginFailed(it.message)
                    },
                    {}
                )
        )
    }
}