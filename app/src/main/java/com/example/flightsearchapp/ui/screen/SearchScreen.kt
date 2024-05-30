package com.example.flightsearchapp.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flightsearchapp.R
import com.example.flightsearchapp.data.Airport
import com.example.flightsearchapp.ui.FlightViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchState: String,
    searchRecommend: List<Airport>,
    favoriteList: List<Pair<Airport, Airport>>,
    onCancelClick: () -> Unit = {},
    onBackHandler: () -> Unit = {},
    onQueryChange: (String) -> Unit,
    onAirportClick: (String) -> Unit,
    viewModel: FlightViewModel,
) {
        Scaffold(
            topBar = {
                SearchBar(
                    modifier = Modifier.height(70.dp),
                    query = searchState,
                    onQueryChange = { onQueryChange(it) },
                    onSearch = {},
                    active = true,
                    onActiveChange = {},
                    leadingIcon = {
                        IconButton(onClick = { onCancelClick() }) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = ImageVector.vectorResource(id = R.drawable.cancel),
                                contentDescription = "",
                            )
                        }
                    },
                ) {}
            }) {
                BackHandler { onBackHandler() }
                SearchList(
                    modifier = Modifier.padding(it).fillMaxWidth(),
                    text = searchState,
                    searchRecommend = searchRecommend,
                    favoriteList = favoriteList,
                    onAirportClick = onAirportClick,
                    viewModel = viewModel)
            }
}

@Composable
fun SearchList(
    modifier: Modifier = Modifier,
    text: String,
    searchRecommend: List<Airport>,
    favoriteList: List<Pair<Airport, Airport>>,
    onAirportClick: (String) -> Unit = {},
    viewModel: FlightViewModel,
) {
    if (text.isEmpty()) {
        // 최근 검색어 표시
        LazyColumn(modifier = modifier) {
            items(favoriteList) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                ) {
                    Text(text = it.first.iataCode)
                    Text(text = it.first.name)
                }
            }
        }
    } else {
        // 추천 검색어 표시
        LazyColumn(modifier = modifier) {
            items(searchRecommend) {
                Row(
                    modifier =
                        Modifier.clickable {
                                viewModel.searchText.value = it.iataCode
                                onAirportClick(it.iataCode)
                            }
                            .padding(24.dp)
                            .fillMaxWidth(),
                ) {
                    Text(text = it.iataCode, modifier = Modifier.padding(horizontal = 8.dp))
                    Text(text = it.name)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    //    FlightSearchAppTheme { SearchScreen(text, Sample.airportSample, Sample.airportSample) }
}
