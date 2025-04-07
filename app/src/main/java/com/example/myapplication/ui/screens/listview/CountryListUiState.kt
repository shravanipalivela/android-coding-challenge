package com.example.myapplication.ui.screens.listview

import com.example.myapplication.domain.model.Country

data class CountryListUiState(
    val isLoading: Boolean = true,
    val countries: List<Country> = emptyList(),
    val error: String? = null
)

