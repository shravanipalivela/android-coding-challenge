package com.example.myapplication.data.api

import com.example.myapplication.data.model.CountryDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface CountriesService {

    @Headers("Content-Type: application/json")
    @GET("v3.1/region/europe")
    suspend fun getCountries(): List<CountryDto>

    @GET("v3.1/name/{name}")
    suspend fun getCountryByName(@Path("name") name: String): List<CountryDto>
}
