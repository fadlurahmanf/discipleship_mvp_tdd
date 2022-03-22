package com.fadlurahmanf.starter_app_mvp.data.repository.study_group

import android.content.Context
import com.fadlurahmanf.starter_app_mvp.base.BasePreference
import com.fadlurahmanf.starter_app_mvp.data.response.study_group.StudyGroupDetailResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudyGroupRepository @Inject constructor(
    var context: Context
): BasePreference(context) {
    var studyGroupDetail:StudyGroupDetailResponse ?= null
}