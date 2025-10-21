package com.sinead.activlog.main

import android.app.Application
import com.sinead.activlog.models.ActivMemStore
import com.sinead.activlog.models.ActivModel
import com.sinead.activlog.models.ActivStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val activs = ActivMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("ActivLog started")
    }
}