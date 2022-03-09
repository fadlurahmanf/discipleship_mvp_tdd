package com.fadlurahmanf.starter_app_mvp.ui.home.presenter

import com.fadlurahmanf.starter_app_mvp.base.BasePresenter
import com.fadlurahmanf.starter_app_mvp.core.extension.uiSubscribe
import com.fadlurahmanf.starter_app_mvp.data.entity.post.PostEntity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import javax.inject.Inject

class GroupBoardPresenter @Inject constructor(
    var postEntity: PostEntity,
    var authRepository: AuthRepository
) : BasePresenter<GroupBoardContract.View>(), GroupBoardContract.Presenter {
    override fun getAllPostFirst() {
        view?.getAllPostFirstLoading()
        addSubscription(postEntity.getAllPost(authRepository.groupChosen!!.id!!).uiSubscribe(
            {
                if (it.code == 100 && it.data != null){
                    view?.getAllPostFirstLoaded(it.data!!)
                }else{
                    view?.getAllPostFailed(it.message)
                }
            },
            {
                view?.getAllPostFailed(it.message)
            },
            {}
        ))
    }

    override fun getAllPost() {
        addSubscription(postEntity.getAllPost(authRepository.groupChosen!!.id!!).uiSubscribe(
            {
                if (it.code == 100 && it.data != null){
                    view?.getAllPostLoaded(it.data!!)
                }else{
                    view?.getAllPostFailed(it.message)
                }
            },
            {
                view?.getAllPostFailed(it.message)
            },
            {}
        ))
    }
}