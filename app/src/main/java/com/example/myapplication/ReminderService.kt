//package com.example.myapplication
//
//import android.Manifest
//import android.app.PendingIntent
//import android.app.Service
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.BitmapFactory
//import android.os.Handler
//import android.os.IBinder
//import android.util.Log
//import androidx.core.app.ActivityCompat
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//
//class ReminderService : Service() {
//    private val handler = Handler()
//    private val notificationId = 101
//    private val CHANNEL_ID = "channel_id_example_01"
//    private val checkInterval = 10 * 1000 // 10 seconds
//    private val PREFS_NAME = "MyPrefsFile"
//    private val LAST_ACTIVE_TIME = "lastActiveTime"
//
//    private lateinit var myApplication: MyApplication
//
//    private val checkRunnable = object : Runnable {
//        override fun run() {
//            val lastActiveTime = getLastActiveTime()
//            val currentTime = System.currentTimeMillis()
//
//            Log.d("ReminderService", "isAppInForeground: ${myApplication?.isAppInForeground}")
//
//            // Check if the MyApplication instance exists and the app is not in the foreground
//            if (!myApplication.isAppInForeground && currentTime - lastActiveTime > checkInterval) {
//                Log.d("ReminderService", "Sending notification...")
//                sendNotificationFromService()
//            } else {
//                Log.d("ReminderService", "Notification conditions not met.")
//            }
//
//            // Always update the last active time, regardless of whether a notification is sent or not
//            updateLastActiveTime()
//
//            // Schedule the next check
//            handler.postDelayed(this, checkInterval.toLong())
//        }
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        // Set the MyApplication reference
//        val appInstance = application
//        if (appInstance is MyApplication) {
//            myApplication = appInstance
//            // Continue with your service logic
//            handler.postDelayed(checkRunnable, checkInterval.toLong())
//        } else {
//            // Log an error or handle the case where the application is not of the expected type.
//        }
//        // Start the periodic check when the service is started
//        return START_STICKY
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        // Stop the periodic check when the service is destroyed
//        handler.removeCallbacks(checkRunnable)
//    }
//
//    private fun updateLastActiveTime() {
//        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//        val currentTime = System.currentTimeMillis()
//        prefs.edit().putLong(LAST_ACTIVE_TIME, currentTime).apply()
//    }
//
//    private fun getLastActiveTime(): Long {
//        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//        return prefs.getLong(LAST_ACTIVE_TIME, 0)
//    }
//
//    private fun sendNotificationFromService() {
//        updateLastActiveTime()
//
//        val intent = Intent(this, MainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//
//        val pendingIntent: PendingIntent =
//            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//
//        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_launcher_foreground)
//        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_launcher_background)
//
//        // Check if the app has the necessary notification permission
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.VIBRATE
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            // Show the notification
//            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentTitle("Reminder!")
//                .setContentText("It's time to play the game!")
//                .setLargeIcon(bitmapLargeIcon)
//                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
//                .setContentIntent(pendingIntent)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//            with(NotificationManagerCompat.from(this)) {
//                notify(notificationId, builder.build())
//            }
//        } else {
//            // Handle the case where notification permission is not granted
//            // You can show a dialog or navigate the user to the settings to grant permission
//            // Example: showNotificationPermissionDialog()
//        }
//    }
//}
