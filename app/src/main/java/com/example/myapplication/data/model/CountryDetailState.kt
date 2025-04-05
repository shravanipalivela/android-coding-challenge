package com.example.myapplication.data.model

data class CountryDetailState(
    val isLoading: Boolean = true,
    val country: Country? = null,
    val error: String? = null
)
