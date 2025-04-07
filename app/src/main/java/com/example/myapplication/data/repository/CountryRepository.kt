package com.example.myapplication.data.repository

import com.example.myapplication.data.model.CountryDto

interface CountryRepository {
    suspend fun loadCountriesDto(): Result<List<CountryDto>>
    suspend fun loadCountryDtoDetails(name: String): Result<List<CountryDto>>
}