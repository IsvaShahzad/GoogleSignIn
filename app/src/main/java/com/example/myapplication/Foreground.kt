//package com.example.myapplication;
//
//import android.app.Activity
//import android.app.Application
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//
//class MyApplication : Application() {
//
//    var isAppInForeground = false
//
//    override fun onCreate() {
//        super.onCreate()
//
//        // Create a notification channel
//        createNotificationChannel()
//
//        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
//            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
//
//            override fun onActivityStarted(activity: Activity) {
//                // App is considered in the foreground when any activity starts
//                isAppInForeground = true
//                Log.d("MyApplication", "App is in the foreground.")
//            }
//
//            override fun onActivityResumed(activity: Activity) {
//                // App is considered in the foreground when any activity is resumed
//                isAppInForeground = true
//                Log.d("MyApplication", "App is in the foreground.")
//            }
//
//            override fun onActivityPaused(activity: Activity) {
//                // App is considered in the background when any activity is paused
//                isAppInForeground = false
//                Log.d("MyApplication", "App is in the background.")
//            }
//
//            override fun onActivityStopped(activity: Activity) {}
//
//            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
//
//            override fun onActivityDestroyed(activity: Activity) {}
//        })
//    }
//
//    private fun createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channelId = "channel_id_example_01"
//            val channelName = "Channel Name"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(channelId, channelName, importance)
//            val notificationManager: NotificationManager =
//                getSystemService(NotificationManager::class.java)
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
//}
