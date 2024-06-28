package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyNotificationWorkManager(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result{
        val data1: String? = inputData.getString("data1")
        val data2: Boolean = inputData.getBoolean ("data2", false)
        val notificationManager: NotificationManager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            val channel : NotificationChannel = NotificationChannel(
                "MY_CHANNEL_ID",
                "MY_CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableLights(true)
            channel.lightColor= Color.RED
            channel.description="MY_NOTIFICATION_CHANNEL_DESCRIPTION"
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(
            applicationContext,
            "MY_CHANNEL_ID")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("P3")
            .setContentText("my service")

        notificationManager.notify(0, notification.build())
        return Result.success()
        //return Result.retry() //senão seriam 15 minutos p/ enviar dnv
    }
}