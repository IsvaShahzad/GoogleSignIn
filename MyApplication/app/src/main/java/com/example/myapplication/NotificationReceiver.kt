package com.example.myapplication;
import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.ReminderService




class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            if (intent.action == "DAILY_NOTIFICATION") {
                // Handle the daily notification
                showDailyNotification(context)
            }
        }
    }

    private fun showDailyNotification(context: Context) {
        // Check for the VIBRATE permission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is granted, proceed with showing the notification
            val CHANNEL_ID = "channel_id_example_01"
            val notificationId = 101

            val builder: NotificationCompat.Builder =
                NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Daily Notification")
                    .setContentText("Your energy levels are refilled")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(context)) {
                notify(notificationId, builder.build())
            }
        } else {
            // You can handle the case where the permission is not granted,
            // and request the permission if needed, or show a different notification.
            // For simplicity, let's log a message in this example.
            Log.d("Notification", "VIBRATE permission not granted")
        }
    }
}
