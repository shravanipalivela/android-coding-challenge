package com.example.myapplication.data.model

import com.example.myapplication.domain.model.Country

data class CountryDetailState(
    val isLoading: Boolean = true,
    val country: Country? = null,
    val error: String? = null
)
