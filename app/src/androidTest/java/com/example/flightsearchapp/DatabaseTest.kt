package com.example.flightsearchapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.flightsearchapp.data.FlightDatabase
import com.example.flightsearchapp.data.SampleData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    val context = InstrumentationRegistry.getInstrumentation().targetContext
    lateinit var instance: FlightDatabase
    val airportSampleData = SampleData.airportSample

    @Before
    fun setUp() {
        instance =
            Room.inMemoryDatabaseBuilder(context.applicationContext, FlightDatabase::class.java)
                .build()
    }

    @Test
    fun test() = runBlocking {
        airportSampleData.map { instance.airportDao().insert(it) }

        val airportList = instance.airportDao().getAllAirport().first()
        Assert.assertEquals(airportList.size, 4)
        Assert.assertEquals(airportList[0], airportSampleData[0])
    }
}
