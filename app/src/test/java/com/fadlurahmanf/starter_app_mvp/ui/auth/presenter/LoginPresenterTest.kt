package com.fadlurahmanf.starter_app_mvp.ui.auth.presenter

import com.fadlurahmanf.starter_app_mvp.core.extension.isValidEmail
import com.fadlurahmanf.starter_app_mvp.data.body.auth.LoginBody
import com.fadlurahmanf.starter_app_mvp.data.entity.auth.AuthEntity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import com.fadlurahmanf.starter_app_mvp.data.response.auth.LoginResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import com.github.ivanshafran.sharedpreferencesmock.SPMockBuilder
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.inOrder

@ExtendWith(MockitoExtension::class)
class LoginPresenterTest{

    @Mock
    lateinit var authEntity: AuthEntity

    @Mock
    lateinit var view:LoginContract.View

    private var spMockBuilder = SPMockBuilder()

    @Mock
    lateinit var authRepository: AuthRepository

    lateinit var presenter: LoginPresenter

    private var loginBody: LoginBody = LoginBody("tffajari@gmail.com", "abcd1234")

    @BeforeEach
    fun beforeEach(){
        MockitoAnnotations.openMocks(this)
        authRepository = AuthRepository(spMockBuilder.createContext())
        presenter = LoginPresenter(authEntity, authRepository)
        presenter.view = view


        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `login_success`(){
        assumeTrue(loginBody.email.isValidEmail())
        assumeTrue(!loginBody.password.isNullOrEmpty())

        var response = BaseResponse<LoginResponse>(code = 100, message = "OK", data = LoginResponse())

        Mockito.`when`(authEntity.login(loginBody)).thenReturn(
            Observable.just(response)
        )

        presenter.login(loginBody.email, loginBody.password)

        val inOrder = inOrder(view, authEntity)

        inOrder.verify(view, Mockito.times(1)).loadingDialog()
        inOrder.verify(authEntity, Mockito.times(1)).login(loginBody)
        inOrder.verify(view, Mockito.times(1)).dismissLoadingDialog()
        assertTrue(presenter.authRepository.password != null)
        inOrder.verify(view, Mockito.times(1)).loginSuccess(response.data!!)
        inOrder.verifyNoMoreInteractions()
    }

    @Test
    fun `login_success but data is null`(){
        assumeTrue(loginBody.email.isValidEmail())
        assumeTrue(!loginBody.password.isNullOrEmpty())
        var response = BaseResponse<LoginResponse>(code = 100, message = "OK", data = null)

        Mockito.`when`(authEntity.login(loginBody)).thenReturn(
            Observable.just(response)
        )

        presenter.login(loginBody.email, loginBody.password)

        val inOrder = inOrder(view, authEntity)

        inOrder.verify(view, Mockito.times(1)).loadingDialog()
        inOrder.verify(authEntity, Mockito.times(1)).login(loginBody)
        inOrder.verify(view, Mockito.times(1)).dismissLoadingDialog()
        inOrder.verify(view, Mockito.times(1)).loginFailed(response.message)
        inOrder.verifyNoMoreInteractions()
    }


    @Test
    fun `login failed`(){
        assumeTrue(loginBody.email.isValidEmail())
        assumeTrue(!loginBody.password.isNullOrEmpty() && loginBody.password.length >= 6)
        val response = BaseResponse<LoginResponse>(code = 999, message = "NOT OK", data = LoginResponse())

        Mockito.`when`(authEntity.login(loginBody)).thenReturn(Observable.just(response))

        presenter.login(loginBody.email, loginBody.password)

        val inOrder = inOrder(view, authEntity)

        inOrder.verify(view, Mockito.times(1)).loadingDialog()
        inOrder.verify(authEntity, Mockito.times(1)).login(loginBody)
        inOrder.verify(view, Mockito.times(1)).dismissLoadingDialog()
        inOrder.verify(view, Mockito.times(1)).loginFailed(response.message)
        inOrder.verifyNoMoreInteractions()
    }
}