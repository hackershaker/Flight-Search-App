package com.example.flightsearchapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.FlightSearchAppTheme
import com.example.flightsearchapp.R
import com.example.flightsearchapp.ui.FlightViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onSearchIconClick: () -> Unit = {}, viewModel: FlightViewModel) {
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
        }) {
            Box(modifier = Modifier.padding(it)) {
                HomeContent(paddingValues = it, viewModel = viewModel)
            }
        }
}

@Composable
fun HomeContent(paddingValues: PaddingValues, viewModel: FlightViewModel) {
    //    val favoriteList = viewModel.getFavoriteList().collectAsState(emptyList())
    val favoriteList = viewModel.favoriteList.collectAsState(emptyList())
    if (favoriteList.value.isEmpty()) {
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
                Text(text = "즐겨찾기한 경로가 없습니다.")
            }
    } else {
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
        ) {
            items(favoriteList.value) {
                FlightCard(
                    modifier = Modifier.padding(8.dp),
                    depart = it.first,
                    arrival = it.second,
                    isCheckedHandler = { start: String, end: String ->
                        viewModel.isFavoriteExists(start, end)
                    },
                    onCheckedChanged = { start: String, end: String ->
                        runBlocking {
                            val favorite = viewModel.inventory.getFavoritebyCode(start, end).first()
                            viewModel.deleteFavorite(favorite)
                        }
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchBar(
    query: String = "test",
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    active: Boolean = false,
    onActiveChange: (Boolean) -> Unit = {}
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
    ) {}
}

@Preview(showBackground = true)
@Composable
fun FlightSearchBarPreview() {
    FlightSearchAppTheme { FlightSearchBar() }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    //    FlightSearchAppTheme { HomeScreen() }
}
