package com.fadlurahmanf.starter_app_mvp.data.entity.core

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.base.network.AbstractNetwork
import com.fadlurahmanf.starter_app_mvp.data.api.core.CheckUpdateApi
import com.fadlurahmanf.starter_app_mvp.data.model.core.CheckUpdateBody
import javax.inject.Inject

class CheckUpdateEntity @Inject constructor(
    var context: Context
): AbstractNetwork<CheckUpdateApi>(context) {
    override fun getApi(): Class<CheckUpdateApi> {
        return CheckUpdateApi::class.java
    }

    fun checkUpdate(body: CheckUpdateBody) = networkService(30).checkUpdate(body = body)
}