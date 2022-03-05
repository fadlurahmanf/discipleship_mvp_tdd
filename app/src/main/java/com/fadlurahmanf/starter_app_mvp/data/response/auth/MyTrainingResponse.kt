package com.fadlurahmanf.starter_app_mvp.data.response.auth

import com.google.gson.annotations.SerializedName

data class MyTrainingResponse(
    @SerializedName("id"        ) var id        : Int?    = null,
    @SerializedName("name"      ) var name      : String? = null,
    @SerializedName("header_url" ) var headerurl : String? = null
)
