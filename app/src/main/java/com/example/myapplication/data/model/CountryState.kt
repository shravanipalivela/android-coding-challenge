package com.example.myapplication.data.model

import com.example.myapplication.domain.model.Country

data class CountryState(
    val isLoading: Boolean = true,
    val countries: List<Country> = emptyList(),
    val error: String? = null
)
