package com.example.myapplication

data class CountryDto(val name: Name) {
    data class Name(val common: String)
}