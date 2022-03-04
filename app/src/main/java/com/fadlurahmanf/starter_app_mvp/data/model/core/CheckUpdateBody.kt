package com.fadlurahmanf.starter_app_mvp.data.model.core

import com.google.gson.annotations.SerializedName

data class CheckUpdateBody (
    @SerializedName("type")
    var type:String,
    @SerializedName("version")
    var version:String,
    @SerializedName("os")
    var os:String
    )