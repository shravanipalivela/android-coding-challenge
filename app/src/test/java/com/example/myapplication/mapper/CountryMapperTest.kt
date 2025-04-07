package com.example.myapplication.mapper

import com.example.myapplication.data.model.CountryDto
import com.example.myapplication.domain.mapper.toCountry
import org.junit.Assert.assertEquals
import org.junit.Test


class CountryMapperTest {
    @Test
    fun `test toCountry() maps CountryDto to Country correctly`() {
        // Given
        val testCountryDto = CountryDto(CountryDto.Name("Germany"), listOf("Berlin"), 80000000, 357000.0, "🇩🇪")

        // When
        val result = testCountryDto.toCountry()

        // Then
        assertEquals("Germany", result.name.common)
        assertEquals("Berlin", result.capital.first())
        assertEquals(80000000, result.population)
    }

}