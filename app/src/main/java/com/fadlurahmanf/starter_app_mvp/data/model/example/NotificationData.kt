package com.fadlurahmanf.starter_app_mvp.data.model.example

import android.app.NotificationManager
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import kotlin.random.Random

data class NotificationData(
    var channelName:String = "General Notification",
    var channelId:String = NotificationConstant.GENERAL_NOTIFICATION_ID,
    var channelDescription:String = "General Notification",
    var notificationId:Int = Random.nextInt(9999),
    var channelImportance: Int? = null,
    var notificationContent:String,
    var notificationTitle:String,
    var notificationPriority :Int = NotificationCompat.PRIORITY_DEFAULT,
    var pendingIntent: PendingIntent ?= null,
)

object NotificationConstant{
    const val GENERAL_NOTIFICATION_ID = "General Notification"
}