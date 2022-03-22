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
        val sdf = SimpleDateFormat("EEE, d MMM yyyy K:mm a")
        return sdf.format(this)
    }catch (e:Exception){
        return null
    }
}

/**
 * Input Date
 * Output Tue, Mar 19
 * */
fun Date.formatDate2():String?{
    try {
        val sdf = SimpleDateFormat("EEE, MMM d")
        return sdf.format(this)
    }catch (e:Exception){
        return null
    }
}

/**
 * Input Date
 * Output Tue, 19 Mar 2020
 * */
fun Date.formatDate3():String?{
    try {
        val sdf = SimpleDateFormat("EEE, d MMM yyyy")
        return sdf.format(this)
    }catch (e:Exception){
        return null
    }
}