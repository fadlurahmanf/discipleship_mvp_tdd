package com.fadlurahmanf.starter_app_mvp.ui.guest_mode

import com.fadlurahmanf.starter_app_mvp.data.entity.core.TestimonialEntity
import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.TestimonialResponse
import com.fadlurahmanf.starter_app_mvp.ui.guest_mode.presenter.GuestModeContract
import com.fadlurahmanf.starter_app_mvp.ui.guest_mode.presenter.GuestModePresenter
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.inOrder

@ExtendWith(MockitoExtension::class)
class GuestModePresenterTest{
    @Mock
    lateinit var testimonialEntity: TestimonialEntity

    lateinit var presenter: GuestModePresenter

    @Mock
    lateinit var view: GuestModeContract.View

    @BeforeEach
    fun beforeEach(){
        presenter = GuestModePresenter(testimonialEntity)
        presenter.view = view

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `testimonialLoaded`(){
        val response = BaseResponse<List<TestimonialResponse>>(code = 100, message = "OK", data = listOf())
        Mockito.`when`(testimonialEntity.getTestimonial()).thenReturn(Observable.just(response))
        presenter.getTestimonial()

        assertTrue(response.code == 100)
        assertTrue(response.message == "OK")
        Mockito.verify(view, Mockito.times(1)).testimonialLoading()
        Mockito.verify(view, Mockito.times(1)).testimonialLoaded(response.data?: listOf())
    }

    @Test
    fun `response code not 100`(){
        val response = BaseResponse<List<TestimonialResponse>>()
        Mockito.`when`(testimonialEntity.getTestimonial()).thenReturn(
            Observable.just(response)
        )
        presenter.getTestimonial()

        val inOrder = inOrder(view, testimonialEntity)
        inOrder.verify(view, Mockito.times(1)).testimonialLoading()
        inOrder.verify(testimonialEntity, Mockito.times(1)).getTestimonial()
        inOrder.verify(view, Mockito.times(1)).testimonialFailed(response.message)
        inOrder.verifyNoMoreInteractions()
    }

    @Test
    fun `throw throwable`(){
        var throwable = Throwable("Exception")
        Mockito.`when`(testimonialEntity.getTestimonial()).thenReturn(
            Observable.error(throwable)
        )
        presenter.getTestimonial()
        val inOrder = inOrder(view, testimonialEntity)
        inOrder.verify(view, Mockito.times(1)).testimonialLoading()
        inOrder.verify(testimonialEntity, Mockito.times(1)).getTestimonial()
        inOrder.verify(view, Mockito.times(1)).testimonialFailed(throwable.message)
        inOrder.verifyNoMoreInteractions()
    }
}