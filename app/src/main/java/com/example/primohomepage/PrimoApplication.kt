package com.example.primohomepage

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PrimoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize other libraries here if needed (e.g., Timber)
    }
}