package com.example.flightsearchapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Insert fun insert(airport: Airport)

    @Query("SELECT * FROM airport") fun getAllAirport(): Flow<List<Airport>>

    @Query(
        "SELECT * FROM airport WHERE iata_code LIKE '%' || :text || '%' OR name LIKE '%' || :text || '%'")
    fun getAllAirportByText(text: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code = :code")
    fun getAirportByCode(code: String): Flow<Airport>
}
