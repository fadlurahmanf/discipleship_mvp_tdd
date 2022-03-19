package com.fadlurahmanf.starter_app_mvp.core.extension

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * Input Date
 * Output Tue, 1 Mar 2022 11:05 AM
 * */
fun Date.formatDate():String?{
    try {
        var sdf = SimpleDateFormat("EEE, d MMM yyyy K:mm a")
        return sdf.format(this)
    }catch (e:Exception){
        return null
    }
}