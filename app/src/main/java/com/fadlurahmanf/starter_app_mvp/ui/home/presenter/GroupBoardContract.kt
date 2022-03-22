package com.fadlurahmanf.starter_app_mvp.ui.home.presenter

import com.fadlurahmanf.starter_app_mvp.base.BaseView
import com.fadlurahmanf.starter_app_mvp.data.response.post.PostResponse

interface GroupBoardContract {
    interface View:BaseView{
        fun getAllPostFirstLoading()
        fun getAllPostFirstLoaded(listPost:List<PostResponse>)
        fun getAllPostLoaded(listPost: List<PostResponse>)
        fun getAllPostFailed(message:String?)

        fun deletePostSuccess()
        fun deletePostFailed(message: String?)

        fun reactPostSuccess()
        fun reactPostFailed(message: String?)
    }
    interface Presenter{
        fun getAllPostFirst()
        fun getAllPost()
        fun deletePost(postId:Int)
        fun reactPost(postId: Int, type:String)
    }
}