// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // base for Android application
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // for Jetpack Compose
    alias(libs.plugins.kotlin.compose) apply false // Compose Compiler Gradle plugin required from Kotlin 2.0
    // for Hilt DI
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
}