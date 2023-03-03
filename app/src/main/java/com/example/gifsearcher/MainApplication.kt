package com.example.gifsearcher

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(object: Timber.DebugTree(){})
    }
}