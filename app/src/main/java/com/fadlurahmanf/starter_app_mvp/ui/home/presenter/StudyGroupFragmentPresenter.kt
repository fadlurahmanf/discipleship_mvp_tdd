package com.fadlurahmanf.starter_app_mvp.ui.home.presenter

import androidx.core.content.ContextCompat
import com.fadlurahmanf.starter_app_mvp.base.BasePresenter
import com.fadlurahmanf.starter_app_mvp.core.extension.uiSubscribe
import com.fadlurahmanf.starter_app_mvp.data.entity.study_group.StudyGroupEntity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import com.fadlurahmanf.starter_app_mvp.data.repository.study_group.StudyGroupRepository
import com.fadlurahmanf.starter_app_mvp.data.response.study_group.StudyGroupDetailResponse
import com.fadlurahmanf.starter_app_mvp.ui.home.tab.StudyGroupFragment
import javax.inject.Inject

class StudyGroupFragmentPresenter @Inject constructor(
    var studyGroupEntity: StudyGroupEntity,
    var authRepository: AuthRepository,
    var studyGroupRepository: StudyGroupRepository
) : BasePresenter<StudyGroupFragmentContract.View>(), StudyGroupFragmentContract.Presenter {
    override fun getStudyGroupDetail() {
        if (authRepository.groupChosen?.id != null){
            view?.getStudyGroupDetailLoading()
            addSubscription(studyGroupEntity.getStudyGroupDetail(authRepository.groupChosen?.id!!).uiSubscribe(
                {
                    if (it.code == 100 && it.data != null){
                        studyGroupRepository.studyGroupDetail = it.data
                        view?.getStudyGroupDetailSuccess()
                    }else{
                        view?.getStudyGroupDetailFailed(it.message)
                    }
                },
                {
                    view?.getStudyGroupDetailFailed(it.message)
                },
                {}
            ))
        }
    }

    override fun getStudyGroupDetailSecond() {
        if (authRepository.groupChosen?.id != null){
            addSubscription(studyGroupEntity.getStudyGroupDetail(authRepository.groupChosen?.id!!).uiSubscribe(
                {
                    if (it.code == 100 && it.data != null){
                        studyGroupRepository.studyGroupDetail = it.data
                        view?.getStudyGroupDetailSuccess()
                    }else{
                        view?.getStudyGroupDetailFailed(it.message)
                    }
                },
                {
                    view?.getStudyGroupDetailFailed(it.message)
                },
                {}
            ))
        }
    }
}