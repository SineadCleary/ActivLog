package com.sinead.activlog.main

import android.app.Application
import com.sinead.activlog.models.ActivJSONStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var activs: ActivJSONStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        activs = ActivJSONStore(applicationContext)
        i("ActivLog started")
    }
}