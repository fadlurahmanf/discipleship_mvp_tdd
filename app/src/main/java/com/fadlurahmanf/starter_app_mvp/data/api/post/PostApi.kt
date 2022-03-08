package com.fadlurahmanf.starter_app_mvp.data.api.post

import androidx.annotation.Nullable
import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import com.fadlurahmanf.starter_app_mvp.data.response.post.PostResponse
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface PostApi {
    @GET("api/post/all")
    fun getAllPost(
        @Query("study_group_id") studyGroupId:String
    ): Observable<BaseResponse<List<PostResponse>>>

    @Multipart
    @POST("api/post/create")
    fun createPost(
        @Part list:List<MultipartBody.Part>
    ):Observable<BaseResponse<Nullable>>

    @DELETE("api/post/delete/{id}")
    fun delete(
        @Path("id") postId:String
    ) : Observable<BaseResponse<Nullable>>
}