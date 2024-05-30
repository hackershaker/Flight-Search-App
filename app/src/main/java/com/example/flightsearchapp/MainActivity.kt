package com.example.flightsearchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.example.compose.FlightSearchAppTheme
import com.example.flightsearchapp.ui.FlightViewModel
import com.example.flightsearchapp.ui.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: FlightViewModel by viewModels { FlightViewModel.Factory }
        setContent {
            Box(modifier = Modifier.safeDrawingPadding()) {
                FlightSearchAppTheme { Navigation(viewModel) }
            }
        }
    }
}
