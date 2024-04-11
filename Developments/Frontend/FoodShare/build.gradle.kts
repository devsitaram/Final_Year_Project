buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false // di
    id("com.android.library") version "7.4.0" apply false
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false // for google map

//    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false // alternative of kapt
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.1" apply false

}