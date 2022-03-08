package com.fadlurahmanf.starter_app_mvp.data.entity.example

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.base.network.AuthAbstractNetwork
import com.fadlurahmanf.starter_app_mvp.data.api.core.TestimonialApi
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import javax.inject.Inject

class ExampleAuthEntity @Inject constructor(
    private var authRepository: AuthRepository,
    private var context: Context
):AuthAbstractNetwork<TestimonialApi>(authRepository, context) {
    override fun getApi(): Class<TestimonialApi> {
        return TestimonialApi::class.java
    }
}