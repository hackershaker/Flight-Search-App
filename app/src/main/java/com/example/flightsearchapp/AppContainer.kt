package com.example.flightsearchapp

import android.app.Application
import com.example.flightsearchapp.data.FlightDatabase
import com.example.flightsearchapp.data.FlightInventory

class AppContainer(application: Application) {
    val context = application.applicationContext
    val database = FlightDatabase.createDb(context)
    val flightInventory = FlightInventory(database)
}
