plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.firebase.crashlytics)
    id("com.google.gms.google-services")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures{
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.androidx.fragment.ktx)

    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))

    // Add the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics.ktx)

    //lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.compiler)

    //glide
    implementation(libs.glide)
    implementation(libs.glide.transformations)
    kapt(libs.compiler)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //retrofit
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.adapter.rxjava2)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.simplexml)

    //chunk
    debugImplementation(libs.library)
    releaseImplementation(libs.library.no.op)

    //flow
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.kotlin.extensions)

    implementation(libs.resultat)
}