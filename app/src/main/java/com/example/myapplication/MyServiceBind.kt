package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import java.util.Random

class MyServiceBind : Service() {
    private val LOG: String= "myService"
    lateinit var notificationManager: NotificationManager
    private val binder: Binder = LocalBinder()
    private val mGenerator= Random()

    val randomNumber: Int
        get ()= mGenerator.nextInt(100)

    override fun onStartCommand (intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(LOG, "onStartCommand")

        val notification = NotificationCompat.Builder(
            this,
            "MY_CHANNEL_ID")
            .setSmallIcon (R.mipmap.ic_launcher)
            .setContentTitle("P3")
            .setContentText("my service")
            .build()

        startForeground(
            100,
            notification)

        return START_STICKY

    }

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG, "service onCreate")

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel: NotificationChannel = NotificationChannel(
                "MY_CHANNEL_ID",
                "MY_CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.description = "MY DESCRIPTION"
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(LOG, "service onDestroy")
    }

    inner class LocalBinder: Binder() {
        fun getService(): MyServiceBind = this@MyServiceBind
    }

    override fun onBind (p0:Intent?): IBinder {
        return binder
    }
}