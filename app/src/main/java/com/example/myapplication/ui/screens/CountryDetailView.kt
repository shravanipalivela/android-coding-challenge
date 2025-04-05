package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.data.model.Country
import com.example.myapplication.ui.viewmodel.CountryDetailViewModel


@Composable
fun CountryDetailView(
    viewModel: CountryDetailViewModel = hiltViewModel(),
    countryName: String,
    navController: NavController
) {
    val countryDetailsState by viewModel.state.collectAsState()

    // Trigger the data load when the screen is launched or countryName changes
    LaunchedEffect(key1 = countryName) {
        viewModel.loadCountryDetails(countryName)
    }

    // Scaffold to hold the TopAppBar and the content
    Scaffold(
        topBar = { TopAppBar(countryDetailsState.country, navController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            // Loading state: Display a loading indicator if data is being fetched
            if (countryDetailsState.isLoading) {
                LoadingScreen(isVisible = countryDetailsState.isLoading)
            }

            // Error state: Display an error indicator if there was an error fetching data
            countryDetailsState.error?.let { errorMessage ->
                ErrorIndicator(errorMessage)
            }

            // Country details view: Only show when data is loaded and no errors
            countryDetailsState.country?.let {
                CountryStateDetailView(it)
            }
        }
    }
}

@Composable
fun CountryStateDetailView(country: Country) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Flag at the top, centered
        Text(
            text = country.flag,
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // Country Name
        Text(
            text = country.name.common,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Details Section inside a Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DetailRow(Icons.Default.LocationOn, "Capital", country.capital.joinToString(", "))
                DetailRow(Icons.Default.People, "Population", country.population.toString())
                DetailRow(Icons.Default.Map, "Size", "${country.area} km²")
            }
        }
    }
}

@Composable
fun DetailRow(icon: ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(text = "$label:", fontWeight = FontWeight.Bold)
        Text(text = value, color = Color.Gray)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(country: Country?, navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = country?.name?.common ?: "Loading...",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}