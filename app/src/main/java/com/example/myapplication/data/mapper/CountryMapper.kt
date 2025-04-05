package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.Country
import com.example.myapplication.data.model.CountryDto

fun CountryDto.toCountry(): Country {
    return Country(
        name = Country.Name(name.common),
        capital = capital,
        population = population,
        area = area,
        flag = flag
    )
}