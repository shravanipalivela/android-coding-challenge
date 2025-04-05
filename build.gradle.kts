// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Project-level build.gradle
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Add this classpath to enable Hilt plugin
        classpath(libs.hilt.android.gradle.plugin) // Make sure the version is correct
        classpath(libs.gradle)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}