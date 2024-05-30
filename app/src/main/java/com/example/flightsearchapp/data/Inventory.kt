package com.example.flightsearchapp.data

import kotlinx.coroutines.flow.Flow

interface Inventory {
    fun getAllAirport(): Flow<List<Airport>>

    fun getAllAirportBySearch(search: String): Flow<List<Airport>>

    fun getAllFavorite(): Flow<List<Favorite>>

    fun getAirportByCode(code: String): Flow<Airport>

    fun isFavoriteExists(departCode: String, destinationCode: String): Flow<Boolean>

    fun getFavoritebyCode(start: String, end: String): Flow<Favorite>

    fun insertFavorite(favorite: Favorite)

    fun deleteFavorite(favorite: Favorite)
}
