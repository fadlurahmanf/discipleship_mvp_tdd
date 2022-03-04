package com.fadlurahmanf.starter_app_mvp.data.body.auth

import com.google.gson.annotations.SerializedName

data class RegisterBody(
    @SerializedName("email")
    var email:String,
    @SerializedName("password")
    var password:String,
    @SerializedName("country_id")
    var countryId:String,
    @SerializedName("role")
    var role:String,
    @SerializedName("firstname")
    var firstName:String,
    @SerializedName("lastname")
    var lastName:String,
    @SerializedName("age_range")
    var ageRange:String,
    @SerializedName("affiliation")
    var affiliationString: String,
    @SerializedName("newsletter")
    var newsletter:Boolean,
    @SerializedName("shared")
    var shared:Boolean
)
