package com.example.p1851notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat



class MainActivity : AppCompatActivity() {

    val longText = "To have a notification appear in an expanded view, " +
            "first create a NotificationCompat.Builder object " +
            "with the normal view options you want. " +
            "Next, call Builder.setStyle() with an " +
            "expanded layout object as its argument."
    val CHANNEL_ID = "12345"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,"My Channel", NotificationManager.IMPORTANCE_HIGH)
            channel.description = "description"
            channel.enableLights(true)
            channel.lightColor = Color.BLUE
            channel.enableVibration(false)
            notificationManager.createNotificationChannel(channel)
        }
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,0)

        val notification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Title")
            .setContentText("Content text")
            .setStyle(NotificationCompat.BigTextStyle().bigText(longText))
            .addAction(R.drawable.ic_launcher_foreground,"Open",pendingIntent)
            .build()
        val secondNotification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Second")
            .setContentText("Some text")
            .build()

        notificationManager.notify(0,notification)
        notificationManager.notify(1,secondNotification)
    }


}
