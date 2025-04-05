package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Country
import com.example.myapplication.data.model.CountryDto

interface CountryRepository {
    suspend fun loadCountries(): Result<List<CountryDto>>
    suspend fun loadCountryDetails(name: String): Result<Country>
}