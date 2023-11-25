package com.example.myapplication;
//import com.facebook.CallbackManager
//import com.facebook.FacebookCallback
//import com.facebook.FacebookSdk
//import com.facebook.appevents.AppEventsLogger
//import com.facebook.login.LoginResult
//import android.app.AlarmManager
//import android.app.AlertDialog
//import android.app.NotificationManager
//import android.app.PendingIntent
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.DialogInterface
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//
//import android.graphics.BitmapFactory
//import android.net.Uri
//import android.os.Build
//import android.os.Bundle
//import android.provider.Settings
//import android.widget.Button
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import androidx.core.content.ContextCompat
//import com.example.myapplication.R
//import com.example.myapplication.ReminderService
//import android.app.Activity;
//import android.app.NotificationChannel
//import com.facebook.FacebookException
//import com.facebook.login.widget.LoginButton
//import java.util.Calendar
//
//
//
//class MainActivity : AppCompatActivity() {
//    private val CHANNEL_ID = "channel_id_example_01"
//    private val notificationId = 101
//    private val checkInterval = 10 * 1000 // 10 seconds
//    private val PREFS_NAME = "MyPrefsFile"
//    private val NOTIFICATION_SETTINGS_REQUEST_CODE = 123
//    private val NOTIFICATION_PERMISSION_GRANTED = "notification_permission_granted"
//
//    // Add a flag to track whether the permission has been requested
//    private var notificationPermissionRequested = false
//
//    // Facebook callback manager
//    private lateinit var callbackManager: CallbackManager
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        // Initialize Facebook SDK
//        FacebookSdk.sdkInitialize(applicationContext)
//        AppEventsLogger.activateApp(application)
//
//
//        // Initialize Facebook login button
//        callbackManager = CallbackManager.Factory.create()
//        val loginButton: LoginButton = findViewById(R.id.login_button)
//
//        // Set permissions (e.g., email)
//        loginButton.setReadPermissions("email")
////        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
////            override fun onSuccess(loginResult: LoginResult?) {
////                // Handle successful login
////            }
////
////            override fun onCancel() {
////                // Handle login cancellation
////            }
////
////            override fun onError(exception: FacebookException) {
////                // Handle login error
////            }
////        })
//
//
//        createNotificationChannel()
//
//        // Check if it's been more than 10 minutes since the user was in the app
//        val lastActiveTime = getLastActiveTime()
//        val currentTime = System.currentTimeMillis()
//        if (currentTime - lastActiveTime > checkInterval) {
//            sendNotifications()
//        }
//
//        val btn_button: Button = findViewById(R.id.btn_button)
//        btn_button.setOnClickListener {
//            if (!checkNotificationPermission()) {
//                // Notifications are not granted, request permission
//                requestNotificationPermission()
//            } else {
//                // Notifications are granted, send notification
//                sendNotifications()
//            }
//        }
//
//        // Start the background service
//        startService(Intent(this, ReminderService::class.java))
//
//        // Schedule daily notification
//        scheduleDailyNotification()
//    }
//
//    private fun scheduleDailyNotification() {
//        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
//        val intent = Intent(this, NotificationReceiver::class.java)
//        intent.action = "DAILY_NOTIFICATION"
//
//        // Set the time for the daily notification (12pm in this case)
//        val calendar = Calendar.getInstance().apply {
//            timeInMillis = System.currentTimeMillis()
//            set(Calendar.HOUR_OF_DAY, 10) // Set to 12 PM (24-hour format)
//            set(Calendar.MINUTE, 12) // Set to 30 minutes
//            set(Calendar.SECOND, 0)
//
//            // If the time is already past, add a day to set it for the next day
//            if (timeInMillis <= System.currentTimeMillis()) {
//                add(Calendar.DAY_OF_MONTH, 1)
//            }
//        }
//
//
//        val pendingIntent =
//            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//        // Set the alarm to trigger at the specified time every day
//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            AlarmManager.INTERVAL_DAY,
//            pendingIntent
//        )
//    }
//
//    private fun sendNotifications() {
//        if (checkNotificationPermission()) {
//            val intent = Intent(this, MainActivity::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            }
//
//            val pendingIntent: PendingIntent =
//                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//
//            val bitmap =
//                BitmapFactory.decodeResource(
//                    applicationContext.resources,
//                    R.drawable.ic_launcher_foreground
//                )
//            val bitmapLargeIcon =
//                BitmapFactory.decodeResource(
//                    applicationContext.resources,
//                    R.drawable.ic_launcher_background
//                )
//
//            val builder: NotificationCompat.Builder =
//                NotificationCompat.Builder(this, CHANNEL_ID)
//                    .setSmallIcon(R.drawable.ic_launcher_foreground)
//                    .setContentTitle("Example Title")
//                    .setContentText("Example Description")
//                    .setLargeIcon(bitmapLargeIcon)
//                    .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
//                    .setContentIntent(pendingIntent)
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//            with(NotificationManagerCompat.from(this)) {
//                notify(notificationId, builder.build())
//            }
//        } else {
//            requestNotificationPermission()
//        }
//    }
//
//    private fun checkNotificationPermission(): Boolean {
//        return NotificationManagerCompat.from(this).areNotificationsEnabled()
//    }
//
//    private fun requestNotificationPermission() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Notification Permission Required")
//        builder.setMessage("Please grant permission to receive notifications.")
//        builder.setPositiveButton("OK") { _, _ ->
//            // Request notification permission
//            openNotificationSettings()
//        }
//        builder.setNegativeButton("Cancel") { dialog, _ ->
//            dialog.dismiss()
//        }
//        builder.show()
//        notificationPermissionRequested = true
//    }
//
//    private fun openNotificationSettings() {
//        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//        val uri = Uri.fromParts("package", packageName, null)
//        intent.data = uri
//        startActivityForResult(intent, NOTIFICATION_SETTINGS_REQUEST_CODE)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == NOTIFICATION_SETTINGS_REQUEST_CODE) {
//            if (checkNotificationPermission()) {
//                setNotificationPermissionGranted()
//                sendNotifications()
//            }
//            notificationPermissionRequested = false
//        }
//    }
//
//    private fun setNotificationPermissionGranted() {
//        val prefs = getPreferences(MODE_PRIVATE)
//        prefs.edit().putBoolean(NOTIFICATION_PERMISSION_GRANTED, true).apply()
//    }
//
//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "Notification Title"
//            val descriptionText = "Notification Description"
//            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                description = descriptionText
//            }
//
//            val notificationManager =
//                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//
//    private val LAST_ACTIVE_TIME = "lastActiveTime"
//
//
//    private fun saveLastActiveTime() {
//        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
//        val editor = prefs.edit()
//        editor.putLong(LAST_ACTIVE_TIME, System.currentTimeMillis())
//        editor.apply()
//    }
//
//    private fun getLastActiveTime(): Long {
//        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
//        return prefs.getLong(LAST_ACTIVE_TIME, 0)
//    }
//}
//

//package com.example.myapplication;

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.ReminderService
import android.app.Activity;
//import android.os.Build.VERSION_CODES.R
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101
    private val checkInterval = 10 * 1000 // 10 seconds
    private val PREFS_NAME = "MyPrefsFile"
    private val NOTIFICATION_SETTINGS_REQUEST_CODE = 123
    private val NOTIFICATION_PERMISSION_GRANTED = "notification_permission_granted"

    // Add a flag to track whether the permission has been requested
    private var notificationPermissionRequested = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        // Check if it's been more than 10 minutes since the user was in the app
        val lastActiveTime = getLastActiveTime()
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastActiveTime > checkInterval) {
            sendNotifications()
        }

        val btn_button: Button = findViewById(R.id.btn_button)
        btn_button.setOnClickListener {
            if (!checkNotificationPermission()) {
                // Notifications are not granted, request permission
                requestNotificationPermission()
            } else {
                // Notifications are granted, send notification
                sendNotifications()
            }
        }

        // Start the background service
        startService(Intent(this, ReminderService::class.java))

        // Schedule daily notification
        scheduleDailyNotification()
    }

    private fun scheduleDailyNotification() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)
        intent.action = "DAILY_NOTIFICATION"

        // Set the time for the daily notification (12pm in this case)
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 10) // Set to 12 PM (24-hour format)
            set(Calendar.MINUTE, 12) // Set to 30 minutes
            set(Calendar.SECOND, 0)

            // If the time is already past, add a day to set it for the next day
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }


        val pendingIntent =
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Set the alarm to trigger at the specified time every day
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun sendNotifications() {
        if (checkNotificationPermission()) {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val bitmap =
                BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_launcher_foreground)
            val bitmapLargeIcon =
                BitmapFactory.decodeResource(applicationContext.resources, R.drawable.ic_launcher_background)

            val builder: NotificationCompat.Builder =
                NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Example Title")
                    .setContentText("Example Description")
                    .setLargeIcon(bitmapLargeIcon)
                    .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, builder.build())
            }
        } else {
            requestNotificationPermission()
        }
    }

    private fun checkNotificationPermission(): Boolean {
        return NotificationManagerCompat.from(this).areNotificationsEnabled()
    }

    private fun requestNotificationPermission() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Notification Permission Required")
        builder.setMessage("Please grant permission to receive notifications.")
        builder.setPositiveButton("OK") { _, _ ->
            // Request notification permission
            openNotificationSettings()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
        notificationPermissionRequested = true
    }

    private fun openNotificationSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, NOTIFICATION_SETTINGS_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NOTIFICATION_SETTINGS_REQUEST_CODE) {
            if (checkNotificationPermission()) {
                setNotificationPermissionGranted()
                sendNotifications()
            }
            notificationPermissionRequested = false
        }
    }

//    @android.support.annotation.RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    private fun setNotificationPermissionGranted() {
        val prefs = getPreferences(Context.MODE_PRIVATE)
        prefs.edit().putBoolean(NOTIFICATION_PERMISSION_GRANTED, true).apply()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private val LAST_ACTIVE_TIME = "lastActiveTime"


    private fun saveLastActiveTime() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong(LAST_ACTIVE_TIME, System.currentTimeMillis())
        editor.apply()
    }

    private fun getLastActiveTime(): Long {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getLong(LAST_ACTIVE_TIME, 0)
    }
}
