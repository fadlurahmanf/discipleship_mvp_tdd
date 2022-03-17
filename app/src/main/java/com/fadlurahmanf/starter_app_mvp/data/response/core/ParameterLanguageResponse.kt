package com.fadlurahmanf.starter_app_mvp.data.response.core

import com.google.gson.annotations.SerializedName

data class ParameterLanguageResponse(
    @SerializedName("name")
    var name:String?=null,
    @SerializedName("code")
    var code:String?=null
)
