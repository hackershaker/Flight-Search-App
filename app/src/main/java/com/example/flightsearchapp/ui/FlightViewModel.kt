package com.example.flightsearchapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearchapp.AppAplication
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.data.Favorite
import com.example.flightsearchapp.data.Inventory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FlightViewModel(val inventory: Inventory) : ViewModel() {
    val searchText = MutableStateFlow("")
    val searchRecommendList =
        inventory
            .getAllAirportBySearch(searchText.value)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis), emptyList())

    private val _favoriteList = MutableStateFlow<List<Pair<Airport, Airport>>>(emptyList())
    val favoriteList = _favoriteList.asStateFlow()

    init {
        getFavoriteListAsMutableState()
    }

    fun getAllAirportList() = inventory.getAllAirport()

    fun getAirportByCode(code: String) = inventory.getAirportByCode(code)

    fun getAirportListSearch(searchText: String) =
        inventory
            .getAllAirportBySearch(searchText)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis), emptyList())

    fun getFavoriteList() =
        inventory.getAllFavorite().map { favorites ->
            favorites.map {
                val a = inventory.getAirportByCode(it.departureCode).first()
                val b = inventory.getAirportByCode(it.destinationCode).first()
                Pair(a, b)
            }
        }

    fun getFavoriteListAsMutableState() =
        viewModelScope.launch {
            inventory
                .getAllFavorite()
                .map { favorites ->
                    favorites.map {
                        val a = inventory.getAirportByCode(it.departureCode).first()
                        val b = inventory.getAirportByCode(it.destinationCode).first()
                        Pair(a, b)
                    }
                }
                .collect { _favoriteList.value = it }
        }

    fun isFavoriteExists(start: String, end: String) =
        inventory
            .isFavoriteExists(start, end)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis), false)

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) { inventory.insertFavorite(favorite) }
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) { inventory.deleteFavorite(favorite) }
    }

    companion object {
        const val stopTimeoutMillis = 5000L

        val Factory = viewModelFactory {
            initializer {
                val inventory = (this[APPLICATION_KEY] as AppAplication).container.flightInventory
                FlightViewModel(inventory = inventory)
            }
        }
    }
}
