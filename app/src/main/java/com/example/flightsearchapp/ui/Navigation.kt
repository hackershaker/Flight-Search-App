package com.example.flightsearchapp.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flightsearchapp.ui.screen.FlightScreen
import com.example.flightsearchapp.ui.screen.HomeScreen
import com.example.flightsearchapp.ui.screen.SearchScreen
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@Composable
fun Navigation(viewModel: FlightViewModel) {
    val navController = rememberNavController()

    val searchText = viewModel.searchText.collectAsState()
    val searchRecommendList = viewModel.getAirportListSearch(searchText.value).collectAsState()
    val favoriteList = viewModel.getFavoriteList().collectAsState(emptyList())

    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            HomeScreen(
                onSearchIconClick = { navController.navigate("search") }, viewModel = viewModel)
        }
        composable(route = "search") {
            SearchScreen(
                searchState = searchText.value,
                searchRecommend = searchRecommendList.value,
                onBackHandler = {
                    Log.d("Navigation", "onBackHandler Called")
                    //                    searchText.value = ""
                    //                    navController.popBackStack("home", true)
                    navController.navigateUp()
                },
                favoriteList = favoriteList.value,
                onQueryChange = { string -> viewModel.searchText.value = string },
                onAirportClick = { code -> navController.navigate("search_result/${code}") },
                viewModel = viewModel)
        }
        composable(
            route = "search_result/{code}",
            arguments = listOf(navArgument("code") { type = NavType.StringType })) {
                val code = it.arguments?.getString("code")
                Log.d("Navigation", "code : $code")
                val startAirport = runBlocking { viewModel.getAirportByCode(code ?: "___").first() }
                val airportList = runBlocking { viewModel.getAllAirportList().first() }
                println(startAirport)
                println(airportList)
                FlightScreen(
                    airportList = airportList,
                    departureAirport = startAirport,
                    onSearchIconClick = { navController.navigate("search") },
                    viewModel = viewModel)
            }
        composable(route = "favorite/{code}") {}
    }
}
