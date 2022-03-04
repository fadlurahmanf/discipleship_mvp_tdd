package com.fadlurahmanf.starter_app_mvp.ui.core

import com.fadlurahmanf.starter_app_mvp.BuildConfig
import com.fadlurahmanf.starter_app_mvp.data.entity.core.CheckUpdateEntity
import com.fadlurahmanf.starter_app_mvp.data.entity.core.LanguageEntity
import com.fadlurahmanf.starter_app_mvp.data.model.core.CheckUpdateBody
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AppRepository
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

@ExtendWith(MockitoExtension::class)
class SplashPresenterTest{

    private var spMockBuilder = SPMockBuilder()

    @Mock
    lateinit var checkUpdateEntity: CheckUpdateEntity

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
        appRepository = AppRepository(spMockBuilder.createContext())
        presenter = SplashPresenter(checkUpdateEntity, languageEntity, appRepository)
        presenter.view = view

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `check_update_and_language_first_time_success`(){
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

        assertTrue(presenter.appRepository.paramsLanguage != null)
        assertTrue(presenter.appRepository.languageResponse != null)
        Mockito.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        Mockito.verify(languageEntity, Mockito.times(1)).getLanguage("en")
        assertEquals(100, updateResponse.code)
        assertEquals(100, languageResponse.code)
        Mockito.verify(view, Mockito.times(1)).checkUpdateLanguageSuccess()
    }

    @Test
    fun `check_update_success_language_not_success`(){
        var checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        var updateResponse = BaseResponse<CheckUpdateResponse>(code = 100, message = "OK", data = CheckUpdateResponse())
        var languageResponse = BaseResponse<LanguageResponse>()

        assertTrue(presenter.appRepository.paramsLanguage == null)
        assertTrue(presenter.appRepository.languageResponse == null)

        Mockito.`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(Observable.just(updateResponse))
        Mockito.`when`(languageEntity.getLanguage("en")).thenReturn(Observable.just(languageResponse))

        presenter.checkUpdateLanguage()

        assertTrue(presenter.appRepository.paramsLanguage == null)
        assertTrue(presenter.appRepository.languageResponse == null)
        Mockito.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        Mockito.verify(languageEntity, Mockito.times(1)).getLanguage("en")
        assertTrue(updateResponse.code == 100)
        assertTrue(languageResponse.code != 100)
        assertTrue(updateResponse.message == "OK")
        assertTrue(languageResponse.message != "OK")
        Mockito.verify(view, Mockito.times(1)).forceRestart(languageResponse.message)
    }

    @Test
    fun `check_update_success_language_throw_error`(){
        var checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        var updateResponse = BaseResponse<CheckUpdateResponse>(code = 100, message = "OK", data = CheckUpdateResponse())
        var throwable = Throwable(message = "MESSAGE THROWABLE")
        var languageResponse = BaseResponse<LanguageResponse>(message = throwable.message)

        assertTrue(presenter.appRepository.paramsLanguage == null)
        assertTrue(presenter.appRepository.languageResponse == null)

        Mockito.lenient().`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(Observable.just(updateResponse))
        Mockito.lenient().`when`(languageEntity.getLanguage("en")).thenReturn(Observable.just(languageResponse))

        presenter.checkUpdateLanguage()

        assertTrue(presenter.appRepository.paramsLanguage == null)
        assertTrue(presenter.appRepository.languageResponse == null)
        Mockito.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        Mockito.verify(languageEntity, Mockito.times(1)).getLanguage("en")
        assertTrue(updateResponse.code == 100)
        assertTrue(languageResponse.code != 100)
        assertTrue(updateResponse.message == "OK")
        assertTrue(languageResponse.message != "OK")
        Mockito.verify(view, Mockito.times(1)).forceRestart(throwable.message)
    }

    @Test
    fun `check_update_not_success_language_success`(){
        var checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        var updateResponse = BaseResponse<CheckUpdateResponse>()
        var languageResponse = BaseResponse<LanguageResponse>(code = 100, message = "OK", data = LanguageResponse())

        assertTrue(presenter.appRepository.paramsLanguage == null)
        assertTrue(presenter.appRepository.languageResponse == null)

        Mockito.`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(Observable.just(updateResponse))
        Mockito.`when`(languageEntity.getLanguage("en")).thenReturn(Observable.just(languageResponse))

        presenter.checkUpdateLanguage()

        assertTrue(presenter.appRepository.paramsLanguage != null)
        assertTrue(presenter.appRepository.languageResponse != null)
        Mockito.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        Mockito.verify(languageEntity, Mockito.times(1)).getLanguage("en")
        assertTrue(updateResponse.code != 100)
        assertTrue(languageResponse.code == 100)
        assertTrue(updateResponse.message != "OK")
        assertTrue(languageResponse.message == "OK")
        Mockito.verify(view, Mockito.times(1)).checkUpdateLanguageFailed(message = updateResponse.message)
    }

    @Test
    fun `check_update_throw_error_language_success`(){
        var checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        var updateResponse = BaseResponse<CheckUpdateResponse>()
        var languageResponse = BaseResponse<LanguageResponse>(code = 100, message = "OK", data = LanguageResponse())

        assertTrue(presenter.appRepository.paramsLanguage == null)
        assertTrue(presenter.appRepository.languageResponse == null)

        Mockito.`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(Observable.just(updateResponse))
        Mockito.`when`(languageEntity.getLanguage("en")).thenReturn(Observable.just(languageResponse))

        presenter.checkUpdateLanguage()

        assertTrue(presenter.appRepository.paramsLanguage != null)
        assertTrue(presenter.appRepository.languageResponse != null)
        Mockito.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        Mockito.verify(languageEntity, Mockito.times(1)).getLanguage("en")
        assertTrue(updateResponse.code != 100)
        assertTrue(languageResponse.code == 100)
        assertTrue(updateResponse.message != "OK")
        assertTrue(languageResponse.message == "OK")
        Mockito.verify(view, Mockito.times(1)).checkUpdateLanguageFailed(message = updateResponse.message)
    }

//    @Test
    fun `check_update_error_language_error`(){
        var checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        var updateResponse = BaseResponse<CheckUpdateResponse>()
        var throwable = Throwable(message = "MESSAGE THROWABLE")
        var languageResponse = BaseResponse<LanguageResponse>()

        assertTrue(presenter.appRepository.paramsLanguage == null)
        assertTrue(presenter.appRepository.languageResponse == null)

        Mockito.`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenThrow(throwable)
        Mockito.`when`(languageEntity.getLanguage("en")).thenReturn(Observable.just(languageResponse))

        presenter.checkUpdateLanguage()

        assertTrue(presenter.appRepository.paramsLanguage != null)
        assertTrue(presenter.appRepository.languageResponse != null)
        Mockito.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        Mockito.verify(languageEntity, Mockito.times(1)).getLanguage("en")
        assertTrue(updateResponse.code != 100)
        assertTrue(languageResponse.code == 100)
        assertTrue(updateResponse.message != "OK")
        assertTrue(languageResponse.message == "OK")
        Mockito.verify(view, Mockito.times(1)).checkUpdateLanguageFailed(message = throwable.message)
    }

    @Test
    fun `just_check_update_success`(){
        var checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        var updateResponse = BaseResponse<CheckUpdateResponse>(code = 100, message = "OK", data = CheckUpdateResponse())

        presenter.appRepository.paramsLanguage = "en"
        presenter.appRepository.languageResponse = LanguageResponse()

        assertTrue(presenter.appRepository.paramsLanguage!=null)
        assertTrue(presenter.appRepository.languageResponse!=null)

        Mockito.lenient().`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(
            Observable.just(updateResponse))

        presenter.checkUpdateLanguage()


        assertTrue(presenter.appRepository.paramsLanguage != null)
        assertTrue(presenter.appRepository.languageResponse != null)
        Mockito.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        Mockito.verify(languageEntity, Mockito.times(0)).getLanguage(presenter.appRepository.paramsLanguage!!)
        Mockito.verify(view, Mockito.times(1)).checkUpdateLanguageSuccess()
    }

    @Test
    fun `just_check_update_failed`(){
        var checkUpdateBody = CheckUpdateBody(type = "app", version = BuildConfig.VERSION_NAME, os = "android")
        var updateResponse = BaseResponse<CheckUpdateResponse>()

        presenter.appRepository.paramsLanguage = "en"
        presenter.appRepository.languageResponse = LanguageResponse()

        Mockito.lenient().`when`(checkUpdateEntity.checkUpdate(checkUpdateBody)).thenReturn(
            Observable.just(updateResponse))

        presenter.checkUpdateLanguage()

        assertTrue(presenter.appRepository.paramsLanguage != null)
        assertTrue(presenter.appRepository.languageResponse != null)
        Mockito.verify(checkUpdateEntity, Mockito.times(1)).checkUpdate(checkUpdateBody)
        Mockito.verify(view, Mockito.times(1)).checkUpdateLanguageFailed(message = updateResponse.message)
    }
}