package com.fadlurahmanf.starter_app_mvp.data.response.post

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("id")
    var id:Int ?= null,
    @SerializedName("accessible")
    var accessible:Boolean ?= null,
    @SerializedName("reacted")
    var reacted:Boolean ?= null,
    @SerializedName("reacted_type")
    var reactedType:String ?= null,
    @SerializedName("study_group_id")
    var studyGroupId:Int ?= null,
    @SerializedName("content")
    var content:String ?= null,
    @SerializedName("created_at")
    var createdAt:String ?= null,
    @SerializedName("updated_at")
    var updatedAt:String ?= null,
    @SerializedName("author")
    var author:Author ?= null,
    @SerializedName("attachments")
    var attachments:ArrayList<Attachment> ?= null,
    @SerializedName("reactions")
    var reactions:ArrayList<Reaction> ?= null,
    @SerializedName("comments")
    var comments:ArrayList<Comment> ?= null
){
    data class Author(
        @SerializedName("id")
        var id:Int ?= null,
        @SerializedName("firstname")
        var firstName:String ?= null,
        @SerializedName("lastname")
        var lastName:String ?= null,
        @SerializedName("photo_url")
        var photoUrl:String ?= null,
        @SerializedName("is_leader")
        var isLeader:Boolean ?= null,
    )
    data class Attachment(
        @SerializedName("id")
        var id:Int ?= null,
        @SerializedName("filename")
        var fileName:String ?= null,
        @SerializedName("type")
        var type:String ?= null,
        @SerializedName("url")
        var url:String ?= null,
    )
    data class Reaction(
        @SerializedName("id")
        var id:Int ?= null,
        @SerializedName("type")
        var type: String ?= null,
        @SerializedName("created_at")
        var createdAt:String ?= null,
        @SerializedName("updated_at")
        var updatedAt:String ?= null,
        @SerializedName("user")
        var user:Reaction.User ?= null,
    ){
        data class User(
            @SerializedName("id")
            var id:Int ?= null,
            @SerializedName("firstname")
            var firstName:String ?= null,
            @SerializedName("lastname")
            var lastName:String ?= null,
            @SerializedName("photo_url")
            var photoUrl:String ?= null,
        )
    }
    data class Comment(
        @SerializedName("id")
        var id:Int ?= null,
        @SerializedName("content")
        var content: String ?= null,
        @SerializedName("vnote_url")
        var vnoteUrl:String ?= null,
        @SerializedName("vnote_duration")
        var vnoteDuration:Int ?= null,
        @SerializedName("created_at")
        var createdAt:String ?= null,
        @SerializedName("updated_at")
        var updatedAt:String ?= null,
        @SerializedName("user")
        var user:Comment.User ?= null,
    ){
        data class User(
            @SerializedName("id")
            var id:Int ?= null,
            @SerializedName("firstname")
            var firstName:String ?= null,
            @SerializedName("lastname")
            var lastName:String ?= null,
            @SerializedName("photo_url")
            var photoUrl:String ?= null,
        )
    }
}
