package com.fadlurahmanf.starter_app_mvp.core.extension

var EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

fun String.isValidEmail():Boolean{
    return this.replace(" ", "").matches(EMAIL_PATTERN.toRegex())
}