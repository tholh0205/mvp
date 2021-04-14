package com.tholh.mvp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationLoader : Application() {

    companion object {
        var applicationContext: Context? = null
    }

    override fun onCreate() {
        try {
            ApplicationLoader.applicationContext = applicationContext
        } catch (ignored: Exception) {
        }
        super.onCreate()
        if (ApplicationLoader.applicationContext == null) {
            ApplicationLoader.applicationContext = applicationContext
        }
    }
}