package com.example.myapplication.ui.screens.detailview

import com.example.myapplication.domain.model.Country

data class CountryDetailUiState(
    val isLoading: Boolean = true,
    val country: Country? = null,
    val error: String? = null
)
