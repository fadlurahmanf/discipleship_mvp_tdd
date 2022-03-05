package com.fadlurahmanf.starter_app_mvp.ui.core

import com.fadlurahmanf.starter_app_mvp.BuildConfig
import com.fadlurahmanf.starter_app_mvp.data.entity.core.CheckUpdateEntity
import com.fadlurahmanf.starter_app_mvp.data.entity.core.LanguageEntity
import com.fadlurahmanf.starter_app_mvp.data.model.core.CheckUpdateBody
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AppRepository
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import com.fadlurahmanf.starter_app_mvp.data.response.auth.LoginResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.CheckUpdateResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.LanguageResponse
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
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.inOrder

@ExtendWith(MockitoExtension::class)
class SplashPresenterTest{

    private var spMockBuilder = SPMockBuilder()

    @Mock
    lateinit var checkUpdateEntity: CheckUpdateEntity

    @Mock
    lateinit var authRepository: AuthRepository

    @Mock
    lateinit var languageEntity: LanguageEntity

    @Mock
    lateinit var appRepository: AppRepository

    @Mock
    lateinit var view:SplashContract.View

    private lateinit var presenter: SplashPresenter

    @BeforeEach
    fun before(){
        MockitoAnnotations.openMocks(this)
        appRepository = Mockito.spy(AppRepository(spMockBuilder.createContext()))
        authRepository = Mockito.spy(AuthRepository(spMockBuilder.createContext()))
        presenter = SplashPresenter(checkUpdateEntity, languageEntity, appRepository, authRepository)
        presenter.view = view

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `check update success get language first time success`(){
        var checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        var updateResponse = BaseResponse<CheckUpdateResponse>(code = 100, message = "OK", data = CheckUpdateResponse())
        var languageResponse = BaseResponse<LanguageResponse>(code = 100, message = "OK", data = LanguageResponse())

        assertTrue(presenter.appRepository.paramsLanguage == null)
        assertTrue(presenter.appRepository.languageResponse == null)

        Mockito.lenient().`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(
            Observable.just(updateResponse)
        )

        Mockito.lenient().`when`(languageEntity.getLanguage("en")).thenReturn(
            Observable.just(languageResponse)
        )

        presenter.checkUpdateLanguage()

        val inOrder = inOrder(view, checkUpdateEntity, languageEntity, appRepository, authRepository)

        assertTrue(updateResponse.code == 100 && updateResponse.message == "OK" && updateResponse.data != null)
        assertTrue(languageResponse.code == 100 && languageResponse.message == "OK" && languageResponse.data != null)

        inOrder.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        inOrder.verify(languageEntity, Mockito.times(1)).getLanguage("en")
        inOrder.verify(presenter.appRepository, Mockito.times(1)).paramsLanguage = "en"
        inOrder.verify(presenter.appRepository, Mockito.times(1)).languageResponse = languageResponse.data
        assertEquals("en", presenter.appRepository.paramsLanguage)
        assertEquals(languageResponse.data, presenter.appRepository.languageResponse)
        inOrder.verify(view, Mockito.times(1)).goToGuestMode(updateResponse.data!!)

        Mockito.verify(view, Mockito.never()).goToLandingPage(updateResponse.data!!)
        Mockito.verify(view, Mockito.never()).checkUpdateLanguageFailed(updateResponse.message)
    }

    @Test
    fun `check update success get language first time not success`(){
        var checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        var updateResponse = BaseResponse<CheckUpdateResponse>(code = 100, message = "OK", data = CheckUpdateResponse())
        var languageResponse = BaseResponse<LanguageResponse>()

        assertTrue(presenter.appRepository.paramsLanguage == null)
        assertTrue(presenter.appRepository.languageResponse == null)

        Mockito.lenient().`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(
            Observable.just(updateResponse)
        )

        Mockito.lenient().`when`(languageEntity.getLanguage("en")).thenReturn(
            Observable.just(languageResponse)
        )

        presenter.checkUpdateLanguage()

        val inOrder = inOrder(view, checkUpdateEntity, languageEntity, appRepository, authRepository)

        assertTrue(updateResponse.code == 100 && updateResponse.message == "OK" && updateResponse.data != null)
        assertTrue(languageResponse.code != 100 || languageResponse.message != "OK" || languageResponse.data == null)

        inOrder.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        inOrder.verify(languageEntity, Mockito.times(1)).getLanguage("en")
        inOrder.verify(presenter.appRepository, Mockito.never()).paramsLanguage = "en"
        inOrder.verify(presenter.appRepository, Mockito.never()).languageResponse = languageResponse.data

        inOrder.verify(view, Mockito.times(1)).forceRestart(languageResponse.message)

        Mockito.verify(view, Mockito.never()).goToLandingPage(updateResponse.data!!)
        Mockito.verify(view, Mockito.never()).goToGuestMode(updateResponse.data!!)
    }

    @Test
    fun `check update success get language first time throw error`(){
        val checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        val updateResponse = BaseResponse<CheckUpdateResponse>(code = 100, message = "OK", data = CheckUpdateResponse())
        val throwableLanguage = Throwable("Throwable Message")

        assertTrue(presenter.appRepository.paramsLanguage == null)
        assertTrue(presenter.appRepository.languageResponse == null)

        Mockito.lenient().`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(
            Observable.just(updateResponse)
        )

        Mockito.lenient().`when`(languageEntity.getLanguage("en")).thenReturn(
            Observable.error(throwableLanguage)
        )

        presenter.checkUpdateLanguage()

        val inOrder = inOrder(view, checkUpdateEntity, languageEntity, appRepository, authRepository)

        inOrder.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        inOrder.verify(languageEntity, Mockito.times(1)).getLanguage("en")
        inOrder.verify(view, Mockito.times(1)).forceRestart(throwableLanguage.message)
    }

    @Test
    fun `check update failed get language first time success`(){
        val checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        val updateResponse = BaseResponse<CheckUpdateResponse>()
        val languageResponse = BaseResponse<LanguageResponse>(code = 100, message = "OK", data = LanguageResponse())

        assertTrue(presenter.appRepository.paramsLanguage == null)
        assertTrue(presenter.appRepository.languageResponse == null)

        Mockito.lenient().`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(
            Observable.just(updateResponse)
        )

        Mockito.lenient().`when`(languageEntity.getLanguage("en")).thenReturn(
            Observable.just(languageResponse)
        )

        presenter.checkUpdateLanguage()

        val inOrder = inOrder(view, checkUpdateEntity, languageEntity, appRepository, authRepository)

        assertTrue(updateResponse.code != 100 || updateResponse.message != "OK" || updateResponse.data == null)
        assertTrue(languageResponse.code == 100 && languageResponse.message == "OK" && languageResponse.data != null)

        inOrder.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        inOrder.verify(languageEntity, Mockito.times(1)).getLanguage("en")
        inOrder.verify(presenter.appRepository, Mockito.times(1)).paramsLanguage = "en"
        inOrder.verify(presenter.appRepository, Mockito.times(1)).languageResponse = languageResponse.data
        assertEquals("en", presenter.appRepository.paramsLanguage)
        assertEquals(languageResponse.data, presenter.appRepository.languageResponse)
        inOrder.verify(view, Mockito.times(1)).goToGuestMode(CheckUpdateResponse())

        Mockito.verify(view, Mockito.never()).goToLandingPage(CheckUpdateResponse())
        Mockito.verify(view, Mockito.never()).checkUpdateLanguageFailed(updateResponse.message)
    }

    @Test
    fun `check update throw error get language first time success`(){
        val checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        val throwableResponse = Throwable("THROWABLE MESSAGE")
        val languageResponse = BaseResponse<LanguageResponse>(code = 100, message = "OK", data = LanguageResponse())

        assertTrue(presenter.appRepository.paramsLanguage == null)
        assertTrue(presenter.appRepository.languageResponse == null)

        Mockito.lenient().`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(
            Observable.error(throwableResponse)
        )

        Mockito.lenient().`when`(languageEntity.getLanguage("en")).thenReturn(
            Observable.just(languageResponse)
        )

        presenter.checkUpdateLanguage()

        val inOrder = inOrder(view, checkUpdateEntity, languageEntity, appRepository, authRepository)

        assertTrue(languageResponse.code == 100 && languageResponse.message == "OK" && languageResponse.data != null)

        inOrder.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        inOrder.verify(languageEntity, Mockito.times(1)).getLanguage("en")
        inOrder.verify(presenter.appRepository, Mockito.times(1)).paramsLanguage = "en"
        inOrder.verify(presenter.appRepository, Mockito.times(1)).languageResponse = languageResponse.data
        assertEquals("en", presenter.appRepository.paramsLanguage)
        assertEquals(languageResponse.data, presenter.appRepository.languageResponse)
        inOrder.verify(view, Mockito.times(1)).goToGuestMode(CheckUpdateResponse())

        Mockito.verify(view, Mockito.never()).goToLandingPage(CheckUpdateResponse())
        Mockito.verify(view, Mockito.never()).checkUpdateLanguageFailed(throwableResponse.message)
    }

    @Test
    fun `check update success not logged in`(){
        var checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        var updateResponse = BaseResponse<CheckUpdateResponse>(code = 100, message = "OK", CheckUpdateResponse())

        presenter.appRepository.paramsLanguage = ""
        presenter.appRepository.languageResponse = LanguageResponse()

        Mockito.`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(Observable.just(updateResponse))

        presenter.checkUpdateLanguage()

        val inOrder = inOrder(view, checkUpdateEntity, languageEntity, appRepository, authRepository)

        assertTrue(presenter.appRepository.paramsLanguage != null)
        assertTrue(presenter.appRepository.languageResponse != null)
        inOrder.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        assertTrue(updateResponse.code == 100)
        assertTrue(updateResponse.message == "OK")
        assertTrue(updateResponse.data != null)
        assertTrue(presenter.appRepository.paramsLanguage != null)
        assertTrue(presenter.appRepository.languageResponse != null)
        Mockito.verify(view, Mockito.times(1)).goToGuestMode(updateResponse.data!!)
    }

    @Test
    fun `check update failed not logged in`(){
        var checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        var updateResponse = BaseResponse<CheckUpdateResponse>()

        presenter.appRepository.paramsLanguage = ""
        presenter.appRepository.languageResponse = LanguageResponse()

        Mockito.`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(Observable.just(updateResponse))

        presenter.checkUpdateLanguage()

        val inOrder = inOrder(view, checkUpdateEntity, languageEntity, appRepository, authRepository)

        assertTrue(presenter.appRepository.paramsLanguage != null)
        assertTrue(presenter.appRepository.languageResponse != null)
        inOrder.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        assertTrue(updateResponse.code != 100)
        assertTrue(updateResponse.message != "OK")
        assertTrue(presenter.appRepository.paramsLanguage != null)
        assertTrue(presenter.appRepository.languageResponse != null)
        Mockito.verify(view, Mockito.times(1)).goToGuestMode(CheckUpdateResponse())
    }

    @Test
    fun `check update success logged in`(){
        var checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        var updateResponse = BaseResponse<CheckUpdateResponse>(code = 100, message = "OK", CheckUpdateResponse())

        presenter.appRepository.paramsLanguage = ""
        presenter.appRepository.languageResponse = LanguageResponse()
        presenter.authRepository.bearerToken = ""
        presenter.authRepository.password = ""
        presenter.authRepository.loginResponse = LoginResponse()

        Mockito.`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(Observable.just(updateResponse))

        presenter.checkUpdateLanguage()

        val inOrder = inOrder(view, checkUpdateEntity, languageEntity, appRepository, authRepository)

        assertTrue(presenter.appRepository.paramsLanguage != null)
        assertTrue(presenter.appRepository.languageResponse != null)
        inOrder.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        assertTrue(updateResponse.code == 100)
        assertTrue(updateResponse.message == "OK")
        assertTrue(updateResponse.data != null)
        assertTrue(presenter.appRepository.paramsLanguage != null)
        assertTrue(presenter.appRepository.languageResponse != null)
        assertTrue(presenter.authRepository.isLoggedIn == true)
        Mockito.verify(view, Mockito.times(1)).goToLandingPage(updateResponse.data!!)
    }

    @Test
    fun `check update failed logged in`(){
        var checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        var updateResponse = BaseResponse<CheckUpdateResponse>()

        presenter.appRepository.paramsLanguage = ""
        presenter.appRepository.languageResponse = LanguageResponse()
        presenter.authRepository.bearerToken = ""
        presenter.authRepository.password = ""
        presenter.authRepository.loginResponse = LoginResponse()

        Mockito.`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(Observable.just(updateResponse))

        presenter.checkUpdateLanguage()

        val inOrder = inOrder(view, checkUpdateEntity, languageEntity, appRepository, authRepository)

        assertTrue(presenter.appRepository.paramsLanguage != null)
        assertTrue(presenter.appRepository.languageResponse != null)
        inOrder.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        assertTrue(updateResponse.code != 100)
        assertTrue(updateResponse.message != "OK")
        assertTrue(presenter.appRepository.paramsLanguage != null)
        assertTrue(presenter.appRepository.languageResponse != null)
        assertTrue(presenter.authRepository.isLoggedIn == true)
        inOrder.verify(view, Mockito.times(1)).goToLandingPage(CheckUpdateResponse())
    }
}