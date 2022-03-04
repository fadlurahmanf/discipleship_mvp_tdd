package com.fadlurahmanf.starter_app_mvp.data.response.core

import com.google.gson.annotations.SerializedName

data class LanguageResponse(
    @SerializedName("guest")
    var guest: GuestModeLanguageResponse ?= null
){
    data class GuestModeLanguageResponse(
        @SerializedName("guest.home.other_saying")
        var guestHomeOtherSaying:String ?= null,
        @SerializedName("guest.home.browse_study")
        var guestHomeBrowseStudy:String ?= null,
        @SerializedName("guest.home.about")
        var guestHomeAbout:String ?= null,
        @SerializedName("guest.home.have_account")
        var guestHomeAccount:String ?= null,
        @SerializedName("guest.home.login")
        var guestHomeLogin:String ?= null
    )
}
