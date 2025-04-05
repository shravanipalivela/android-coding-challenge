package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import com.example.myapplication.data.model.CountryDto
import com.example.myapplication.ui.viewmodel.CountryListViewModel
import  androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CountriesListView(
    viewModel: CountryListViewModel = hiltViewModel(),
    navController: NavController
) {
    val countriesState by viewModel.countriesState.collectAsState()

    // Pull refresh state
    val pullRefreshState = rememberPullRefreshState(refreshing = countriesState.isLoading,onRefresh = { viewModel.loadCountries() })

    // Trigger fetching countries when the screen is first launched
    LaunchedEffect(Unit)  {
            viewModel.loadCountries()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Pull to refresh
        Column(modifier = Modifier.pullRefresh(pullRefreshState)) {
            // Loading screen
            if (countriesState.isLoading) {
                LoadingScreen(isVisible = true)
            }
            // Error state
            countriesState.error?.let { errorMessage ->
                ErrorIndicator(errorMessage)
            }

            // Country list content
            CountryStateListView(countriesState.countries, navController)
        }

        // Pull to refresh indicator
        PullRefreshIndicator(
            refreshing = countriesState.isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun ErrorIndicator(error: String?) {
    error?.let { errorMessage ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Error: $errorMessage",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun LoadingScreen(isVisible: Boolean, modifier: Modifier = Modifier) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag("loadingIndicator"),
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun CountryStateListView(countriesList: List<CountryDto>, navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = { AppHeader() }) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(countriesList) { item ->
                CountryCard(
                    country = item,
                    navController = navController,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader() {
   TopAppBar(title = {
       Text("Fun With Countries")
   },colors = TopAppBarDefaults.largeTopAppBarColors(
       containerColor = MaterialTheme.colorScheme.primary,
       titleContentColor = MaterialTheme.colorScheme.onPrimary
   ))
}

@Composable
fun CountryCard(country: CountryDto, navController: NavController, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier
            .padding(16.dp)
            .clickable {
                navController.navigate("countryDetail/${country.name.common}") // Handle navigation to detail screen
            },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = country.flag,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = country.name.common,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = "Capital: ${country.capital.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

        }
    }
}
