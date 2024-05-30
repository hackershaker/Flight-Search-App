package com.example.flightsearchapp

import com.example.flightsearchapp.fake.FakeFlightInventory
import com.example.flightsearchapp.ui.FlightViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ViewmodelTest {
    private val viewModel = FlightViewModel(FakeFlightInventory())

    @Test
    fun fake_inventory_test() = runBlocking {
        val fakeInventory = FakeFlightInventory()
        val list = fakeInventory.getAllAirportBySearch("airport").toList().first()
        println(list)
        assertEquals(list.size, 5)
    }

    @Test
    fun viewModel_getAirportListSearch_test() = runBlocking {
        viewModel.searchText.value = "i"
        println(viewModel.searchText.value)
        val recommendList = viewModel.getAirportListSearch(viewModel.searchText.value).first()
        println("recommend list: $recommendList")
        assertEquals(recommendList.size, 5)
    }
}
