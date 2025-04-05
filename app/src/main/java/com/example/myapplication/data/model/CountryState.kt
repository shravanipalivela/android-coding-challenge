package com.example.myapplication.data.model

data class CountryState(
    val isLoading: Boolean = true,
    val countries: List<CountryDto> = emptyList(),
    val error: String? = null
)
