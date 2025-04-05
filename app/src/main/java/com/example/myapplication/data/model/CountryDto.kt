package com.example.myapplication.data.model

data class CountryDto(
    val name: Name,
    val capital: List<String>,
    val population: Int,
    val area: Double,
    val flag: String
) {
    data class Name(val common: String)
}