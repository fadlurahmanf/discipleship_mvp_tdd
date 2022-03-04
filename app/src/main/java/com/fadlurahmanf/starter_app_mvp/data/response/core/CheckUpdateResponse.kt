package com.fadlurahmanf.starter_app_mvp.data.response.core

import com.google.gson.annotations.SerializedName

data class CheckUpdateResponse (
    @SerializedName("updated")
    var updated:Boolean ?= null,
    @SerializedName("forced")
    var forced:Boolean ?= null,
    @SerializedName("version")
    var version:String ?= null
)