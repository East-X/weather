package com.eastx7.weather

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApp : Application() {

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
    }

}