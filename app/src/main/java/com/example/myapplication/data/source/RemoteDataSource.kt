package com.example.myapplication.data.source

import com.example.myapplication.data.model.CountryDto

interface RemoteDataSource {
    suspend fun fetchCountriesDto(): Result<List<CountryDto>>
    suspend fun fetchCountryDtoByName(name: String): Result<List<CountryDto>>

}