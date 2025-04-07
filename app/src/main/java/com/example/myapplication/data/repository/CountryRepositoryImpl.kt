package com.example.myapplication.data.repository

import com.example.myapplication.data.model.CountryDto
import com.example.myapplication.data.source.RemoteDataSource
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    CountryRepository {
    override suspend fun loadCountriesDto(): Result<List<CountryDto>> {
        return remoteDataSource.fetchCountriesDto()
    }

    override suspend fun loadCountryDtoDetails(name: String): Result<List<CountryDto>> {
        return remoteDataSource.fetchCountryDtoByName(name)
    }

}