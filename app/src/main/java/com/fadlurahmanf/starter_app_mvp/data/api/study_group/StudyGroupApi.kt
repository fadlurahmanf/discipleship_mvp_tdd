package com.fadlurahmanf.starter_app_mvp.data.api.study_group

import androidx.annotation.Nullable
import com.fadlurahmanf.starter_app_mvp.data.response.core.BaseResponse
import com.fadlurahmanf.starter_app_mvp.data.response.study_group.StudyGroupDetailResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudyGroupApi {
    @GET("api/study-group/detail/{studyId}")
    fun getStudyGroupDetail(
        @Path("studyId") studyId:String
    ) : Observable<BaseResponse<StudyGroupDetailResponse>>

    @POST("api/study-group/edit/detail")
    fun editStudyGroupDetail() : Observable<BaseResponse<Nullable>>
}