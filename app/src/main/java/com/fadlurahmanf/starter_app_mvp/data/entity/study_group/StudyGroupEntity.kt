package com.fadlurahmanf.starter_app_mvp.data.entity.study_group

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.base.network.AuthAbstractNetwork
import com.fadlurahmanf.starter_app_mvp.data.api.study_group.StudyGroupApi
import com.fadlurahmanf.starter_app_mvp.data.entity.core.TestimonialEntity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import javax.inject.Inject

class StudyGroupEntity @Inject constructor(
    authRepository: AuthRepository,
    context: Context,
    testimonialEntity: TestimonialEntity
) : AuthAbstractNetwork<StudyGroupApi>(authRepository, context, testimonialEntity){
    override fun getApi(): Class<StudyGroupApi> {
        return StudyGroupApi::class.java
    }

    fun getStudyGroupDetail(studyId:Int) = networkService(30).getStudyGroupDetail(studyId.toString())

}