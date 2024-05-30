package com.example.flightsearchapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flightsearchapp.R
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.data.Favorite
import com.example.flightsearchapp.data.sample.Sample
import com.example.flightsearchapp.ui.FlightViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightScreen(
    departureAirport: Airport,
    airportList: List<Airport>,
    onSearchIconClick: () -> Unit,
    viewModel: FlightViewModel,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Flight Search App") },
                actions = {
                    Row {
                        IconButton(onClick = { onSearchIconClick() }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector =
                                    ImageVector.vectorResource(id = R.drawable.search_icon),
                                contentDescription = "Search Icon")
                        }
                    }
                })
        }) { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(airportList) {
                    if (departureAirport.iataCode != it.iataCode) {
                        FlightCard(
                            modifier = Modifier.padding(8.dp),
                            depart = departureAirport,
                            arrival = it,
                            isCheckedHandler = { start: String, end: String ->
                                viewModel.isFavoriteExists(start, end)
                            },
                            onCheckedChanged = { start: String, end: String ->
                                runBlocking {
                                    val favoriteExist = viewModel.isFavoriteExists(start, end).value
                                    if (favoriteExist) {
                                        val favorite =
                                            viewModel.inventory
                                                .getFavoritebyCode(start, end)
                                                .first()
                                        viewModel.deleteFavorite(favorite)
                                    } else {
                                        val favorite =
                                            Favorite(
                                                id = 0,
                                                departureCode = start,
                                                destinationCode = end)
                                        viewModel.insertFavorite(favorite)
                                    }
                                }
                            })
                    }
                }
            }
        }
}

@Composable
fun FlightCard(
    modifier: Modifier = Modifier,
    depart: Airport,
    arrival: Airport,
    isCheckedHandler: (String, String) -> StateFlow<Boolean>,
    onCheckedChanged: (start: String, end: String) -> Unit,
) {
    val isChecked = isCheckedHandler(depart.iataCode, arrival.iataCode).collectAsState()

    Card(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(4f).padding(8.dp).fillMaxWidth()) {
                Text(text = "DEPART", fontSize = 16.sp)
                Row {
                    Text(
                        text = depart.iataCode,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier =
                            Modifier.background(
                                    MaterialTheme.colorScheme.background, CutCornerShape(4.dp))
                                .padding(horizontal = 4.dp))
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = depart.name, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = "ARRIVAL", fontFamily = MaterialTheme.typography.labelMedium.fontFamily)
                Row {
                    Text(
                        text = arrival.iataCode,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier =
                            Modifier.background(
                                    MaterialTheme.colorScheme.background, CutCornerShape(4.dp))
                                .padding(horizontal = 4.dp))
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = arrival.name, fontSize = 14.sp)
                }
            }
            IconToggleButton(
                modifier = Modifier.weight(1f),
                checked = isChecked.value,
                onCheckedChange = { onCheckedChanged(depart.iataCode, arrival.iataCode) }) {
                    if (isChecked.value) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Favorite toggle button")
                    } else {
                        Icon(
                            painterResource(id = R.drawable.baseline_star_outline_24),
                            contentDescription = "Favorite toggle button")
                    }
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlightCardPreview() {
    val airportList = Sample.airportSample
    FlightCard(
        modifier = Modifier,
        depart = airportList[0],
        arrival = airportList[1],
        isCheckedHandler = { _: String, _: String -> MutableStateFlow(false) },
        onCheckedChanged = { _: String, _: String -> })
}
