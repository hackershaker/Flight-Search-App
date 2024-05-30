package com.example.flightsearchapp

import android.app.Application

class AppAplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
