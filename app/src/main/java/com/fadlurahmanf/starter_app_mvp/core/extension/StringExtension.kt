package com.fadlurahmanf.starter_app_mvp.core.extension

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

var EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

fun String.isValidEmail():Boolean{
    return this.replace(" ", "").matches(EMAIL_PATTERN.toRegex())
}

/**
 * Input 2022-05-23
 * Output May 23, 2022
 * */
fun String.formatDate():String?{
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
            val date = LocalDate.parse(this)
            return date.format(formatter)
        } else {
            return null
        }
    }catch (e:Exception){
        return null
    }
}

/**
 * Input 2022-03-01T11:05:00.000Z
 * Output Date
 * */

fun String.isoDateTimeToDate():Date?{
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            return Date.from(Instant.parse(this))
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(this)
        }
        return null
    }catch (e:Exception){
        return null
    }
}