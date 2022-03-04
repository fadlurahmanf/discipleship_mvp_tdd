package com.fadlurahmanf.starter_app_mvp.data.response.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    var accessToken:String?=null,
    @SerializedName("user")
    var user:User?=null
){
    data class User(
        @SerializedName("firstname")
        var firstName:String?=null,
        @SerializedName("lastname")
        var lastName:String?=null,
        @SerializedName("email")
        var email:String?=null,
        @SerializedName("role")
        var role:String?=null,
        @SerializedName("age_range")
        var ageRange:String?=null,
        @SerializedName("country")
        var country:String?=null,
        @SerializedName("affiliation")
        var affiliation:String?=null,
        @SerializedName("activated")
        var activated:Boolean?=null,
        @SerializedName("newsletter")
        var newsletter:Boolean?=null,
        @SerializedName("shared_email")
        var sharedEmail:Boolean?=null,
        @SerializedName("shared_phone")
        var sharedPhone:Boolean?=null,
        @SerializedName("subscriptions")
        var subscriptions:ArrayList<User.Subscription>?=null,
        @SerializedName("groups")
        var groups:ArrayList<User.Group>?=null
    ){
        data class Subscription(
            @SerializedName("package_id")
            var packageId:Int?=null,
            @SerializedName("type")
            var type:String?=null,
            @SerializedName("trained")
            var trained:Boolean?=null,
        ){
            data class Study(
                @SerializedName("id")
                var id:Int?=null,
                @SerializedName("name")
                var name:String?=null,
                @SerializedName("header_url")
                var headerUrl:String?=null
            )
        }
        data class Group(
            @SerializedName("id")
            var id:Int?=null,
            @SerializedName("code")
            var code:String?=null,
            @SerializedName("weekly_meeting_time")
            var weeklyMeetingTime:String?=null,
        ){
            data class Study(
                @SerializedName("id")
                var id:Int?=null,
                @SerializedName("name")
                var name:String?=null,
            )
            data class Leader(
                @SerializedName("id")
                var id:Int?=null,
                @SerializedName("firstname")
                var firstName:String?=null,
                @SerializedName("lastname")
                var lastName: String?=null,
            )
        }
    }
}
