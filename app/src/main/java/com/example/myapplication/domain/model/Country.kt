package com.example.myapplication.domain.model

data class Country(
    val name: Name,
    val capital: List<String>,
    val population: Int,
    val area: Double,
    val flag: String
) {
    data class Name(val common: String)
}
