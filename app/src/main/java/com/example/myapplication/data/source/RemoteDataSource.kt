package com.example.myapplication.data.source

import com.example.myapplication.data.model.Country
import com.example.myapplication.data.model.CountryDto

interface RemoteDataSource {
    suspend fun fetchCountries(): Result<List<CountryDto>>
    suspend fun fetchCountryByName(name: String): Result<Country>

}