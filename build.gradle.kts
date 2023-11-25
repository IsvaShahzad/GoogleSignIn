buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        classpath ("com.android.tools.build:gradle:7.0.3")

    }
}
////// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}



//plugins {
//    id("com.android.application")
//    kotlin("android") version "1.8.0" // Use a version known to be compatible
//}
