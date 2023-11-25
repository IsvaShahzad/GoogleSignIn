package com.example.myapplication;
import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat



class ReminderService : Service() {
    private val handler = Handler()
    private val notificationId = 101
    private val CHANNEL_ID = "channel_id_example_01"
    private val checkInterval = 10 * 1000 // 10 seconds
    private val PREFS_NAME = "MyPrefsFile"
    private val LAST_ACTIVE_TIME = "lastActiveTime"

    private val checkRunnable = object : Runnable {
        override fun run() {
            // Check if it's been more than 10 seconds since the user was in the app
            val lastActiveTime = getLastActiveTime()
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastActiveTime > checkInterval) {
                sendNotificationFromService()
            }

            // Schedule the next check
            handler.postDelayed(this, checkInterval.toLong())
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start the periodic check when the service is started
        handler.postDelayed(checkRunnable, checkInterval.toLong())
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop the periodic check when the service is destroyed
        handler.removeCallbacks(checkRunnable)
    }

    private fun getLastActiveTime(): Long {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getLong(LAST_ACTIVE_TIME, 0)
    }

    private fun sendNotificationFromService() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_launcher_foreground)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_launcher_background)

        // Check if the app has the necessary notification permission
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.VIBRATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Show the notification
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Reminder!")
                .setContentText("It's time to play the game!")
                .setLargeIcon(bitmapLargeIcon)
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, builder.build())
            }
        } else {
            // Handle the case where notification permission is not granted
            // You can show a dialog or navigate the user to the settings to grant permission
            // Example: showNotificationPermissionDialog()
        }
    }
}
