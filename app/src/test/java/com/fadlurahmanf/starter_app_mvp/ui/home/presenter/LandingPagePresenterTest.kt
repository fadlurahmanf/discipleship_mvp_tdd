package com.fadlurahmanf.starter_app_mvp.ui.home.presenter

import com.fadlurahmanf.starter_app_mvp.data.entity.auth.AuthEntity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import com.fadlurahmanf.starter_app_mvp.data.response.auth.LoginResponse
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyGroupResponse
import com.fadlurahmanf.starter_app_mvp.data.response.auth.MyTrainingResponse
import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import com.github.ivanshafran.sharedpreferencesmock.SPMockBuilder
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.inOrder

@ExtendWith(MockitoExtension::class)
class LandingPagePresenterTest{
    private var spMockBuilder = SPMockBuilder()

    private lateinit var authRepository: AuthRepository

    @Mock
    lateinit var authEntity: AuthEntity

    @Mock
    lateinit var view:LandingPageContract.View

    private lateinit var presenter: LandingPagePresenter

    @BeforeEach
    fun beforeEach(){
        MockitoAnnotations.openMocks(this)
        authRepository = Mockito.spy(AuthRepository(spMockBuilder.createContext()))
        presenter = LandingPagePresenter(authEntity, authRepository)
        presenter.view = view

        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `get my group success get my subscription success`(){
        var groupResponse = BaseResponse<List<MyGroupResponse>>(
            code = 100,
            message = "OK",
            data = listOf()
        )
        var subscriptionResponse = BaseResponse<List<LoginResponse.User.Subscription>>(
            code = 100,
            message = "OK",
            data = listOf()
        )

        authRepository.bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inpha3VuaW4xMTQwQGdtYWlsLmNvbSIsInN1YiI6Niwicm9sZSI6InBhcnRpY2lwYW50IiwiaWF0IjoxNjQyNDk2MjcxfQ.h7mGkLtlKM_E5qKX0iIJKasgcVFC3uckKJ4Nz1E0Lgg"
        assertTrue(!authRepository.bearerToken.isNullOrEmpty())
        assertTrue(authRepository.bearerToken?.contains("Bearer ") == true)
        assertTrue(authRepository.bearerToken?.replace("Bearer ", "")?.isNotEmpty() == true)

        Mockito.`when`(authEntity.getMyGroups(presenter.authRepository.bearerToken!!)).thenReturn(
            Observable.just(groupResponse)
        )

        Mockito.`when`(authEntity.getSubscription(presenter.authRepository.bearerToken!!)).thenReturn(
            Observable.just(subscriptionResponse)
        )

        presenter.getMyGroupAndMySubscription()

        val inOrder = inOrder(view, authEntity, authRepository)

        inOrder.verify(view, Mockito.times(1)).myGroupAndSubscriptionLoading()
        inOrder.verify(authEntity, Mockito.times(1)).getMyGroups(presenter.authRepository.bearerToken!!)
        inOrder.verify(authEntity, Mockito.times(1)).getSubscription(presenter.authRepository.bearerToken!!)
        assertTrue(groupResponse.code == 100)
        assertTrue(groupResponse.data != null)
        assertTrue(subscriptionResponse.code == 100)
        assertTrue(subscriptionResponse.data != null)
        inOrder.verify(presenter.authRepository, Mockito.times(1)).myGroup = groupResponse.data
        inOrder.verify(presenter.authRepository, Mockito.times(1)).mySubscription = subscriptionResponse.data
        inOrder.verify(view, Mockito.times(1)).myGroupAndMySubscriptionLoaded(groupResponse.data!!, subscriptionResponse.data!!)
    }

    @Test
    fun `get my group success get my subscription failed`(){
        var groupResponse = BaseResponse<List<MyGroupResponse>>(
            code = 100,
            message = "OK",
            data = listOf()
        )
        var subscriptionResponse = BaseResponse<List<LoginResponse.User.Subscription>>(
            code = 999,
            message = "NOT OK",
        )

        authRepository.bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inpha3VuaW4xMTQwQGdtYWlsLmNvbSIsInN1YiI6Niwicm9sZSI6InBhcnRpY2lwYW50IiwiaWF0IjoxNjQyNDk2MjcxfQ.h7mGkLtlKM_E5qKX0iIJKasgcVFC3uckKJ4Nz1E0Lgg"
        assertTrue(!authRepository.bearerToken.isNullOrEmpty())
        assertTrue(authRepository.bearerToken?.contains("Bearer ") == true)
        assertTrue(authRepository.bearerToken?.replace("Bearer ", "")?.isNotEmpty() == true)

        Mockito.`when`(authEntity.getMyGroups(presenter.authRepository.bearerToken!!)).thenReturn(
            Observable.just(groupResponse)
        )

        Mockito.`when`(authEntity.getSubscription(presenter.authRepository.bearerToken!!)).thenReturn(
            Observable.just(subscriptionResponse)
        )

        presenter.getMyGroupAndMySubscription()

        val inOrder = inOrder(view, authEntity, authRepository)

        inOrder.verify(view, Mockito.times(1)).myGroupAndSubscriptionLoading()
        inOrder.verify(authEntity, Mockito.times(1)).getMyGroups(presenter.authRepository.bearerToken!!)
        inOrder.verify(authEntity, Mockito.times(1)).getSubscription(presenter.authRepository.bearerToken!!)
        assertTrue(groupResponse.code == 100 && subscriptionResponse.code != 100)
        inOrder.verify(authRepository, Mockito.never()).myGroup = groupResponse.data
        inOrder.verify(authRepository, Mockito.never()).mySubscription = subscriptionResponse.data
        inOrder.verify(view, Mockito.times(1)).myGroupAndMySubscriptionError(subscriptionResponse.message)
    }

    @Test
    fun `get my group success get my subscription throw error`(){
        var groupResponse = BaseResponse<List<MyGroupResponse>>(
            code = 100,
            message = "OK",
            data = listOf()
        )
        var throwableSubscription = Throwable("THROWABLE MESSAGE")

        authRepository.bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inpha3VuaW4xMTQwQGdtYWlsLmNvbSIsInN1YiI6Niwicm9sZSI6InBhcnRpY2lwYW50IiwiaWF0IjoxNjQyNDk2MjcxfQ.h7mGkLtlKM_E5qKX0iIJKasgcVFC3uckKJ4Nz1E0Lgg"
        assertTrue(!authRepository.bearerToken.isNullOrEmpty())
        assertTrue(authRepository.bearerToken?.contains("Bearer ") == true)
        assertTrue(authRepository.bearerToken?.replace("Bearer ", "")?.isNotEmpty() == true)

        Mockito.`when`(authEntity.getMyGroups(presenter.authRepository.bearerToken!!)).thenReturn(
            Observable.just(groupResponse)
        )

        Mockito.`when`(authEntity.getSubscription(presenter.authRepository.bearerToken!!)).thenReturn(
            Observable.error(throwableSubscription)
        )

        presenter.getMyGroupAndMySubscription()

        val inOrder = inOrder(view, authEntity, authRepository)

        inOrder.verify(view, Mockito.times(1)).myGroupAndSubscriptionLoading()
        inOrder.verify(authEntity, Mockito.times(1)).getMyGroups(presenter.authRepository.bearerToken!!)
        inOrder.verify(authEntity, Mockito.times(1)).getSubscription(presenter.authRepository.bearerToken!!)
        inOrder.verify(view, Mockito.times(1)).myGroupAndMySubscriptionError(throwableSubscription.message)
    }

    @Test
    fun `get my group failed get my subscription success`(){
        var groupResponse = BaseResponse<List<MyGroupResponse>>(
            code = 999,
            message = "NOT OK",
            data = listOf()
        )
        var subscriptionResponse = BaseResponse<List<LoginResponse.User.Subscription>>(
            code = 100,
            message = "OK",
            data = listOf()
        )

        authRepository.bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inpha3VuaW4xMTQwQGdtYWlsLmNvbSIsInN1YiI6Niwicm9sZSI6InBhcnRpY2lwYW50IiwiaWF0IjoxNjQyNDk2MjcxfQ.h7mGkLtlKM_E5qKX0iIJKasgcVFC3uckKJ4Nz1E0Lgg"
        assertTrue(!authRepository.bearerToken.isNullOrEmpty())
        assertTrue(authRepository.bearerToken?.contains("Bearer ") == true)
        assertTrue(authRepository.bearerToken?.replace("Bearer ", "")?.isNotEmpty() == true)

        Mockito.`when`(authEntity.getMyGroups(presenter.authRepository.bearerToken!!)).thenReturn(
            Observable.just(groupResponse)
        )

        Mockito.`when`(authEntity.getSubscription(presenter.authRepository.bearerToken!!)).thenReturn(
            Observable.just(subscriptionResponse)
        )

        presenter.getMyGroupAndMySubscription()

        val inOrder = inOrder(view, authEntity, authRepository)

        inOrder.verify(view, Mockito.times(1)).myGroupAndSubscriptionLoading()
        inOrder.verify(authEntity, Mockito.times(1)).getMyGroups(presenter.authRepository.bearerToken!!)
        inOrder.verify(authEntity, Mockito.times(1)).getSubscription(presenter.authRepository.bearerToken!!)
        inOrder.verify(presenter.authRepository, Mockito.never()).myGroup = groupResponse.data
        inOrder.verify(presenter.authRepository, Mockito.never()).mySubscription = subscriptionResponse.data
        inOrder.verify(view, Mockito.times(1)).myGroupAndMySubscriptionError(groupResponse.message)
    }

    @Test
    fun `get my group throw error get my subscription success`(){
        var throwableGroup = Throwable("THROWABLE MESSAGE")
        var subscriptionResponse = BaseResponse<List<LoginResponse.User.Subscription>>(
            code = 100,
            message = "OK",
            data = listOf()
        )

        authRepository.bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inpha3VuaW4xMTQwQGdtYWlsLmNvbSIsInN1YiI6Niwicm9sZSI6InBhcnRpY2lwYW50IiwiaWF0IjoxNjQyNDk2MjcxfQ.h7mGkLtlKM_E5qKX0iIJKasgcVFC3uckKJ4Nz1E0Lgg"
        assertTrue(!authRepository.bearerToken.isNullOrEmpty())
        assertTrue(authRepository.bearerToken?.contains("Bearer ") == true)
        assertTrue(authRepository.bearerToken?.replace("Bearer ", "")?.isNotEmpty() == true)

        Mockito.`when`(authEntity.getMyGroups(presenter.authRepository.bearerToken!!)).thenReturn(
            Observable.error(throwableGroup)
        )

        Mockito.`when`(authEntity.getSubscription(presenter.authRepository.bearerToken!!)).thenReturn(
            Observable.just(subscriptionResponse)
        )

        presenter.getMyGroupAndMySubscription()

        val inOrder = inOrder(view, authEntity, authRepository)

        inOrder.verify(view, Mockito.times(1)).myGroupAndSubscriptionLoading()
        inOrder.verify(authEntity, Mockito.times(1)).getMyGroups(presenter.authRepository.bearerToken!!)
        inOrder.verify(authEntity, Mockito.times(1)).getSubscription(presenter.authRepository.bearerToken!!)
        inOrder.verify(view, Mockito.times(1)).myGroupAndMySubscriptionError(throwableGroup.message)
    }

    @Test
    fun `get my training success`(){
        var response = BaseResponse<List<MyTrainingResponse>>(
            code = 100,
            message = "OK",
            data = listOf()
        )

        authRepository.bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inpha3VuaW4xMTQwQGdtYWlsLmNvbSIsInN1YiI6Niwicm9sZSI6InBhcnRpY2lwYW50IiwiaWF0IjoxNjQyNDk2MjcxfQ.h7mGkLtlKM_E5qKX0iIJKasgcVFC3uckKJ4Nz1E0Lgg"
        assertTrue(!authRepository.bearerToken.isNullOrEmpty())
        assertTrue(authRepository.bearerToken?.contains("Bearer ") == true)
        assertTrue(authRepository.bearerToken?.replace("Bearer ", "")?.isNotEmpty() == true)

        Mockito.`when`(
            authEntity.getMyTrainingStudyTopic(authRepository.bearerToken!!)
        ).thenReturn(Observable.just(response))

        presenter.getMyTraining()

        val inOrder = inOrder(view, authEntity)

        inOrder.verify(view, Mockito.times(1)).getTrainingLoading()
        inOrder.verify(authEntity, Mockito.times(1)).getMyTrainingStudyTopic(authRepository.bearerToken!!)
        inOrder.verify(view, Mockito.times(1)).getTrainingLoaded(response.data!!)
    }

    @Test
    fun `get my training success but data is null`(){
        var response = BaseResponse<List<MyTrainingResponse>>(
            code = 100,
            message = "OK",
            data = null
        )

        authRepository.bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inpha3VuaW4xMTQwQGdtYWlsLmNvbSIsInN1YiI6Niwicm9sZSI6InBhcnRpY2lwYW50IiwiaWF0IjoxNjQyNDk2MjcxfQ.h7mGkLtlKM_E5qKX0iIJKasgcVFC3uckKJ4Nz1E0Lgg"
        assertTrue(!authRepository.bearerToken.isNullOrEmpty())
        assertTrue(authRepository.bearerToken?.contains("Bearer ") == true)
        assertTrue(authRepository.bearerToken?.replace("Bearer ", "")?.isNotEmpty() == true)

        Mockito.`when`(
            authEntity.getMyTrainingStudyTopic(authRepository.bearerToken!!)
        ).thenReturn(Observable.just(response))

        presenter.getMyTraining()

        val inOrder = inOrder(view, authEntity)

        inOrder.verify(view, Mockito.times(1)).getTrainingLoading()
        inOrder.verify(authEntity, Mockito.times(1)).getMyTrainingStudyTopic(authRepository.bearerToken!!)
        inOrder.verify(view, Mockito.times(1)).myTrainingFailed(response.message)
    }

    @Test
    fun `get my training failed`(){
        var response = BaseResponse<List<MyTrainingResponse>>(
            code = 542,
            message = "NOT OK",
            data = null
        )

        authRepository.bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inpha3VuaW4xMTQwQGdtYWlsLmNvbSIsInN1YiI6Niwicm9sZSI6InBhcnRpY2lwYW50IiwiaWF0IjoxNjQyNDk2MjcxfQ.h7mGkLtlKM_E5qKX0iIJKasgcVFC3uckKJ4Nz1E0Lgg"
        assertTrue(!authRepository.bearerToken.isNullOrEmpty())
        assertTrue(authRepository.bearerToken?.contains("Bearer ") == true)
        assertTrue(authRepository.bearerToken?.replace("Bearer ", "")?.isNotEmpty() == true)

        Mockito.`when`(
            authEntity.getMyTrainingStudyTopic(authRepository.bearerToken!!)
        ).thenReturn(Observable.just(response))

        presenter.getMyTraining()

        val inOrder = inOrder(view, authEntity)

        inOrder.verify(view, Mockito.times(1)).getTrainingLoading()
        inOrder.verify(authEntity, Mockito.times(1)).getMyTrainingStudyTopic(authRepository.bearerToken!!)
        inOrder.verify(view, Mockito.times(1)).myTrainingFailed(response.message)
        assertTrue(response.code != 100)
        assertTrue(response.message != "OK")
    }

    @Test
    fun `get my training throw error`(){
        var throwable = Throwable(message = "THROWABLE MESSAGE")

        authRepository.bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6Inpha3VuaW4xMTQwQGdtYWlsLmNvbSIsInN1YiI6Niwicm9sZSI6InBhcnRpY2lwYW50IiwiaWF0IjoxNjQyNDk2MjcxfQ.h7mGkLtlKM_E5qKX0iIJKasgcVFC3uckKJ4Nz1E0Lgg"
        assertTrue(!authRepository.bearerToken.isNullOrEmpty())
        assertTrue(authRepository.bearerToken?.contains("Bearer ") == true)
        assertTrue(authRepository.bearerToken?.replace("Bearer ", "")?.isNotEmpty() == true)

        Mockito.`when`(
            authEntity.getMyTrainingStudyTopic(authRepository.bearerToken!!)
        ).thenReturn(Observable.error(throwable))

        presenter.getMyTraining()

        val inOrder = inOrder(view, authEntity)

        inOrder.verify(view, Mockito.times(1)).getTrainingLoading()
        inOrder.verify(authEntity, Mockito.times(1)).getMyTrainingStudyTopic(authRepository.bearerToken!!)
        inOrder.verify(view, Mockito.times(1)).myTrainingFailed(throwable.message)
    }
}