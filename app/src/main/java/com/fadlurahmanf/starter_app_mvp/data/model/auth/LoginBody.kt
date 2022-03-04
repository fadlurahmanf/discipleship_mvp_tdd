package com.fadlurahmanf.starter_app_mvp.data.body.auth

import com.google.gson.annotations.SerializedName

data class LoginBody(
    @SerializedName("email")
    var email:String,
    @SerializedName("password")
    var password:String
)
