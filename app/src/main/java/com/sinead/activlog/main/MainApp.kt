package com.sinead.activlog.main

import android.app.Application
import com.sinead.activlog.models.ActivModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val activs = ArrayList<ActivModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("ActivLog started")
    }
}