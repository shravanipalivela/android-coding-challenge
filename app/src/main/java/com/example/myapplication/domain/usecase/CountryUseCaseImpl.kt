package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.Country
import com.example.myapplication.data.repository.CountryRepository

import javax.inject.Inject

class CountryUseCaseImpl @Inject constructor(private val repository: CountryRepository) :
    CountryUseCase {
    override suspend fun loadCountries(): Result<List<Country>> {

        return repository.loadCountriesDto().map { dtoList ->
            dtoList.map { dto ->
                Country(
                    name = Country.Name(dto.name.common),
                    capital = dto.capital,
                    population = dto.population,
                    area = dto.area,
                    flag = dto.flag
                )
            }
        }
    }

    override suspend fun loadCountryDetails(name: String): Result<List<Country>> {

        return repository.loadCountryDtoDetails(name).map { dtoList ->
            dtoList.map { dto ->
                Country(
                    name = Country.Name(dto.name.common),
                    capital = dto.capital,
                    population = dto.population,
                    area = dto.area,
                    flag = dto.flag
                )
            }
        }
    }

}