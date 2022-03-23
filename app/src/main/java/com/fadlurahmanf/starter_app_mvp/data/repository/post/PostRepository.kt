package com.fadlurahmanf.starter_app_mvp.data.repository.post

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.base.BasePreference
import com.fadlurahmanf.starter_app_mvp.core.constant.ParamsKeySP
import com.fadlurahmanf.starter_app_mvp.data.response.post.PostResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(context:Context):BasePreference(context) {

    var listPost:ArrayList<PostResponse>?=null

}