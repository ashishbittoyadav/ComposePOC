// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
//    alias(libs.plugins.org.jetbrains.kotlin.kapt) apply false

    id("com.google.devtools.ksp") version "1.9.23-1.0.19" apply false

    alias(libs.plugins.org.jetbrains.kotlin.kapt) apply false


    //hilt
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    alias(libs.plugins.androidLibrary) apply false
}