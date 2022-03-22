package com.fadlurahmanf.starter_app_mvp.core.extension

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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

/**
 * Input Date
 * Output Jun 15, 2020
 * */
fun Date.formatDate4():String?{
    try {
        val sdf = SimpleDateFormat("MMM d, yyyy")
        return sdf.format(this)
    }catch (e:Exception){
        return null
    }
}

/**
 * Input Date
 * Output [just now or 6h ago or 3d ago or Jun 15, 2021]
 * */
fun Date.differenceWithNow():String?{
    try {
        val diffInMilliSecond = Calendar.getInstance().timeInMillis - this.time
        val diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMilliSecond)
        val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMilliSecond)
        val diffInDay = TimeUnit.MILLISECONDS.toDays(diffInMilliSecond)
        if (diffInMinutes < 1){
            return "just now"
        }else if (diffInHours < 1){
            return "${diffInMinutes}m ago"
        } else if (diffInDay < 1){
            return "${diffInHours}h ago"
        }else if (diffInDay < 7){
            return "${diffInDay}d ago"
        }else{
            return this.formatDate4()
        }
    }catch (e:Exception){
        return null
    }
}