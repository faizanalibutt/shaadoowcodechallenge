package com.test.shaadoow

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.test.shaadoow.ui.activity.MainActivity
import timber.log.Timber


class AppDelegate : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        appContext = applicationContext
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    companion object {
        @JvmStatic
        var appContext: Context? = null
    }
}