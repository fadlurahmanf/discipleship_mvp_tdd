package com.fadlurahmanf.starter_app_mvp.data.response.auth

import com.google.gson.annotations.SerializedName

data class MyGroupResponse(
    @SerializedName("status" ) var status: String?  = null,
    @SerializedName("code"   ) var code  : String?  = null,
    @SerializedName("participant"    ) var participant   : Int?     = null,
    @SerializedName("role"   ) var role  : String?  = null,
    @SerializedName("change_allowed" ) var changeAllowed : Boolean? = null,
    @SerializedName("study"  ) var study : Study ?= null
){
    data class Study(
        @SerializedName("id"                  ) var id                : Int?    = null,
        @SerializedName("name"                ) var name              : String? = null,
        @SerializedName("leader"              ) var leader            : String? = null,
        @SerializedName("weekly_meeting_time" ) var weeklyMeetingTime : String? = null
    )
}
