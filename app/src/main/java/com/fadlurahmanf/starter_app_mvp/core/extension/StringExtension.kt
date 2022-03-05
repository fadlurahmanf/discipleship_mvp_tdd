package com.fadlurahmanf.starter_app_mvp.core.extension

import android.os.Build
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

var EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

fun String.isValidEmail():Boolean{
    return this.replace(" ", "").matches(EMAIL_PATTERN.toRegex())
}

/**
 * Input ()
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