package com.yun.mysimpletravel

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MySimpleTravelApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}