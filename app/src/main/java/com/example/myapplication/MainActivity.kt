package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.CountriesListView
import com.example.myapplication.ui.screens.CountryDetailView
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CountryApp()
            }
        }
    }
}

@Composable
fun CountryApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "countryList") {
        composable("countryList") {
            CountriesListView(navController = navController)
        }
        composable(
            "countryDetail/{countryName}",
            arguments = listOf(navArgument("countryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val countryName = backStackEntry.arguments?.getString("countryName")
            if (countryName != null) {
                CountryDetailView(countryName = countryName, navController = navController)
            }

        }
    }
}

