package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Country
import com.example.myapplication.data.model.CountryDto
import com.example.myapplication.data.source.RemoteDataSource
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    CountryRepository {
    override suspend fun loadCountries(): Result<List<CountryDto>> {
        return remoteDataSource.fetchCountries()
    }

    override suspend fun loadCountryDetails(name: String): Result<Country> {
        return remoteDataSource.fetchCountryByName(name)
    }

}