package com.fadlurahmanf.starter_app_mvp.data.entity.post

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.base.network.AuthAbstractNetwork
import com.fadlurahmanf.starter_app_mvp.data.api.post.PostApi
import com.fadlurahmanf.starter_app_mvp.data.entity.core.TestimonialEntity
import com.fadlurahmanf.starter_app_mvp.data.interceptor.AuthInterceptor
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PostEntity @Inject constructor(
    private var authRepository: AuthRepository,
    private var context: Context,
    private var testimonialEntity: TestimonialEntity
): AuthAbstractNetwork<PostApi>(authRepository, context, testimonialEntity) {
    override fun getApi(): Class<PostApi> {
        return PostApi::class.java
    }

    fun getAllPost(studyGroupId:Int) = networkService(30).getAllPost(studyGroupId.toString())

    fun createPost(body: List<MultipartBody.Part>) = networkService(30).createPost(body)

    fun deletePost(postId:String) = networkService(30).delete(postId)
}