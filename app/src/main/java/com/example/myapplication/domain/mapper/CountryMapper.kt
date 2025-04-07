package com.example.myapplication.domain.mapper

import com.example.myapplication.domain.model.Country
import com.example.myapplication.data.model.CountryDto

fun CountryDto.toCountry(): Country {
    return Country(
        name = Country.Name(common = this.name.common),
        capital = this.capital,
        population = this.population,
        area = this.area,
        flag = this.flag
    )
}

fun List<CountryDto>.toCountryList(): List<Country> {
   return this.map { it.toCountry() }
}
