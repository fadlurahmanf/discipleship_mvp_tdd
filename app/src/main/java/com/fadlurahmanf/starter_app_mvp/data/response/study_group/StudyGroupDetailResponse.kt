package com.fadlurahmanf.starter_app_mvp.data.response.study_group

import com.google.gson.annotations.SerializedName

data class StudyGroupDetailResponse (
    @SerializedName("id"                  ) var id                : Int?     = null,
    @SerializedName("weekly_meeting_time" ) var weeklyMeetingTime : String?  = null,
    @SerializedName("orientation_date"    ) var orientationDate   : String?  = null,
    @SerializedName("start_date"          ) var startDate         : String?  = null,
    @SerializedName("slot"                ) var slot              : Int?     = null,
    @SerializedName("capacity"            ) var capacity          : Int?     = null,
    @SerializedName("private"             ) var private           : Boolean? = null,
    @SerializedName("co_leader_email"     ) var coLeaderEmail     : String?  = null,
    @SerializedName("leader"              ) var leader            : Leader?  = null,
    @SerializedName("study"               ) var study             : Study?   = null,
    @SerializedName("zoom"                ) var zoom              : Zoom?    = null,
    @SerializedName("member"              ) var member            : Member?  = null,
    @SerializedName("meeting"             ) var meeting           : Meeting? = null
){
    data class Leader (
        @SerializedName("id"        ) var id        : Int?    = null,
        @SerializedName("firstname" ) var firstname : String? = null,
        @SerializedName("lastname"  ) var lastname  : String? = null,
        @SerializedName("biography" ) var biography : String? = null

    )
    data class Study (
        @SerializedName("id"   ) var id   : Int?    = null,
        @SerializedName("name" ) var name : String? = null
    )
    data class Zoom (
        @SerializedName("ur"       ) var ur       : String? = null,
        @SerializedName("id"       ) var id       : Int?    = null,
        @SerializedName("passcode" ) var passcode : String? = null
    )
    data class Member (
        @SerializedName("total" ) var total : Int?             = null,
        @SerializedName("datas" ) var datas : ArrayList<MemberDetail>? = null
    ){
        data class MemberDetail (
            @SerializedName("id"           ) var id          : Int?     = null,
            @SerializedName("firstname"    ) var firstname   : String?  = null,
            @SerializedName("lastname"     ) var lastname    : String?  = null,
            @SerializedName("photo"        ) var photo       : String?  = null,
            @SerializedName("role"         ) var role        : String?  = null,
            @SerializedName("email"        ) var email       : String?  = null,
            @SerializedName("shared_email" ) var sharedEmail : Boolean? = null,
            @SerializedName("phone"        ) var phone       : String?  = null,
            @SerializedName("shared_phone" ) var sharedPhone : Boolean? = null
        )
    }
    data class Meeting (
        @SerializedName("total" ) var total : Int?             = null,
        @SerializedName("datas" ) var datas : ArrayList<MeetingDetail>? = null
    ){
        data class MeetingDetail (
            @SerializedName("id"       ) var id       : Int?    = null,
            @SerializedName("session"  ) var session  : Int?    = null,
            @SerializedName("datetime" ) var datetime : String? = null

        )
    }
}