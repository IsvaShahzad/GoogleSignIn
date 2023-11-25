plugins {
    id ("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")

//    kotlin("android") version "1.9.0"
//    kotlin("android") version "1.5.31" // Use a version known to be compatible

}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34  // Target Android 13
        versionCode = 1
        versionName = "1.0"
        renderscriptTargetApi=29
        renderscriptSupportModeEnabled=true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {

        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

//    packagingOptions {
//        exclude("META-INF/DEPENDENCIES")
//        exclude("META-INF/NOTICE")
//        exclude("META-INF/LICENSE")
//    }
    kotlinOptions{
        jvmTarget="1.8"
    }

    compileOptions{
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation ("com.facebook.android:facebook-login:16.0.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    testImplementation("junit:junit:4.13.2")
    implementation ("com.google.android.gms:play-services-auth:20.2.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")





    // Facebook SDK
//
//    // Google Sign-In SDK
//    implementation("com.google.android.gms:play-services-auth:latest_version")
//
//    // Twitter SDK
//    implementation("com.twitter.sdk.android:twitter-core:latest_version")
//    implementation("com.twitter.sdk.android:twitter-login:latest_version")
}
