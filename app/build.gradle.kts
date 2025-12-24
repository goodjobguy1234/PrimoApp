plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.primohomepage"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.primohomepage"
        minSdk = 24
        targetSdk = 36
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // unit test
    testImplementation(libs.junit)

    // instrumental test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // di
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // api fetcher
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.xml)

    // image loader
    implementation(libs.glide)
    kapt(libs.glide.compiler)

    // local database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx) // Provides Coroutine support (suspend functions)
    kapt(libs.androidx.room.compiler)
}