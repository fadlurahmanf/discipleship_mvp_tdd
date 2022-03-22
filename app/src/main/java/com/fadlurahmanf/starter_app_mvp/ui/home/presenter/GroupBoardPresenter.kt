package com.fadlurahmanf.starter_app_mvp.ui.home.presenter

import com.fadlurahmanf.starter_app_mvp.base.BasePresenter
import com.fadlurahmanf.starter_app_mvp.core.extension.isoDateTimeToDate
import com.fadlurahmanf.starter_app_mvp.core.extension.uiSubscribe
import com.fadlurahmanf.starter_app_mvp.data.entity.post.PostEntity
import com.fadlurahmanf.starter_app_mvp.data.repository.core.AuthRepository
import com.google.gson.JsonObject
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
                    view?.getAllPostFirstLoaded(it.data!!.sortedByDescending { it -> it.createdAt?.isoDateTimeToDate() })
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

    override fun deletePost(postId: Int) {
        view?.loadingDialog()
        addSubscription(postEntity.deletePost(postId).uiSubscribe(
            {
                view?.dismissLoadingDialog()
                if (it.code == 100){
                    view?.deletePostSuccess()
                }else{
                    view?.deletePostFailed(it.message)
                }
            },
            {
                view?.deletePostFailed(it.message)
            },
            {}
        ))
    }

    override fun reactPost(postId: Int, type: String) {
        var json = JsonObject()
        json.addProperty("post_id", postId)
        json.addProperty("type", type)
        addSubscription(postEntity.reactPost(json).uiSubscribe(
            {
                if (it.code == 100){
                    view?.reactPostSuccess()
                }else{
                    view?.reactPostFailed(it.message)
                }
            },
            {
                view?.reactPostFailed(it.message)
            },
            {}
        ))
    }
}