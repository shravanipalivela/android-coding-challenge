package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Country

interface CountryUseCase {

    suspend fun loadCountries(): Result<List<Country>>
    suspend fun loadCountryDetails(name: String): Result<List<Country>>

}