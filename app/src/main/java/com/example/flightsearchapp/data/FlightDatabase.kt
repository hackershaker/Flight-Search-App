package com.example.flightsearchapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database([Airport::class, Favorite::class], version = 1)
abstract class FlightDatabase() : RoomDatabase() {
    abstract fun airportDao(): AirportDao

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private var instance: FlightDatabase? = null

        fun createDb(context: Context): FlightDatabase {
            return instance
                ?: synchronized(this) {
                    Room.databaseBuilder(context, FlightDatabase::class.java, "flight_database")
                        .createFromAsset("database/flight_search.db")
                        .build()
                        .also { instance = it }
                }
        }
    }
}
