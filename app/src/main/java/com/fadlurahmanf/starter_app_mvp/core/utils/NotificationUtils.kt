package com.fadlurahmanf.starter_app_mvp.core.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.data.model.example.NotificationData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationUtils @Inject constructor(var context: Context) {

    companion object{
        const val CHANNEL_ID = "DEFAULT_CHANNEL_ID"
    }

    private var notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder


    private fun buildNotification(data: NotificationData){
        notificationBuilder = NotificationCompat.Builder(context.applicationContext, data.channelName)
            .setSmallIcon(R.drawable.ic_discipleship)
            .setContentTitle(data.notificationTitle)
            .setContentText(data.notificationContent)
            .setContentIntent(data.pendingIntent)
            .setPriority(data.notificationPriority)
    }

    private fun createNotificationChannel(data: NotificationData){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channelName = data.channelName
            val channelId = data.channelId
            val channelDescription = data.channelName
            val importance = data.channelImportance ?: NotificationManager.IMPORTANCE_DEFAULT
            var channel = NotificationChannel(channelName, channelId, importance).apply {
                description = channelDescription
            }
            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(data: NotificationData){
        createNotificationChannel(data)
        buildNotification(data)
        notificationManager.notify(data.notificationId, notificationBuilder.build())
    }

}