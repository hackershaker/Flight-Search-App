package com.example.flightsearchapp.data

import kotlinx.coroutines.flow.Flow

class FlightInventory(database: FlightDatabase) : Inventory {
    private val airportDao = database.airportDao()
    private val favoriteDao = database.favoriteDao()

    override fun getAllAirport() = airportDao.getAllAirport()

    override fun getAllAirportBySearch(search: String) = airportDao.getAllAirportByText(search)

    override fun getAllFavorite(): Flow<List<Favorite>> = favoriteDao.getAllFavorite()

    override fun getAirportByCode(code: String) = airportDao.getAirportByCode(code)

    override fun isFavoriteExists(departCode: String, destinationCode: String): Flow<Boolean> =
        favoriteDao.isFavoriteExists(departCode, destinationCode)

    override fun getFavoritebyCode(start: String, end: String): Flow<Favorite> =
        favoriteDao.getFavoriteByCode(start, end)

    override fun insertFavorite(favorite: Favorite) = favoriteDao.insert(favorite)

    override fun deleteFavorite(favorite: Favorite) = favoriteDao.delete(favorite)
}
