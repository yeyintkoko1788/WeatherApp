// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
    kotlin("plugin.serialization") version "1.9.22"
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
}