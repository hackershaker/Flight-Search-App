package com.example.flightsearchapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo("departure_code") val departureCode: String,
    @ColumnInfo("destination_code") val destinationCode: String
)
