package com.example.flightsearchapp.fake

import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.data.Favorite
import com.example.flightsearchapp.data.Inventory
import com.example.flightsearchapp.data.SampleData
import com.example.flightsearchapp.data.sample.Sample
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFlightInventory : Inventory {
    override fun getAllAirport(): Flow<List<Airport>> = flow { SampleData.airportSample }

    override fun getAllAirportBySearch(search: String): Flow<List<Airport>> {
        println("search string: $search")
        return flow {
            emit(
                Sample.airportSample.filter {
                    it.name.contains(search, true) || it.iataCode.contains(search, true)
                })
        }
    }

    override fun getAllFavorite(): Flow<List<Favorite>> {
        TODO("Not yet implemented")
    }

    override fun getAirportByCode(code: String): Flow<Airport> {
        TODO("Not yet implemented")
    }

    override fun isFavoriteExists(departCode: String, destinationCode: String): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getFavoritebyCode(start: String, end: String): Flow<Favorite> {
        TODO("Not yet implemented")
    }

    override fun insertFavorite(favorite: Favorite) {
        TODO("Not yet implemented")
    }

    override fun deleteFavorite(favorite: Favorite) {
        TODO("Not yet implemented")
    }
}
