package com.example.myapplication.repository

import com.example.myapplication.data.model.CountryDto
import com.example.myapplication.data.repository.CountryRepositoryImpl
import com.example.myapplication.data.source.RemoteDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CountryRepositoryTest {

    private val remoteDataSource: RemoteDataSource = mockk()  // Mocking the remote data source
    private val repository = CountryRepositoryImpl(remoteDataSource)  // The class that contains the `loadCountries` function

    @Test
    fun `fetchCountries should return a success result with a list of countries`(): Unit = runBlocking {

        //Given
        val mockCountries = listOf(
            CountryDto(CountryDto.Name("Germany"), listOf("Berlin"), 80000000, 357000.0, "🇩🇪"),
            CountryDto(CountryDto.Name("France"), listOf("Paris"), 67000000, 640679.0, "🇫🇷")
        )
        coEvery{remoteDataSource.fetchCountriesDto()} returns Result.success(mockCountries) // Mocking the remote call

        // When
        val result = repository.loadCountriesDto()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size) // Check if the list size is correct
        assertEquals("Germany", result.getOrNull()?.get(0)?.name?.common) // Verify the first country's name
    }



    @Test
    fun `fetchCountries should return a failure result when the remote call fails`() = runBlocking {
        // Given
        coEvery{remoteDataSource.fetchCountriesDto()} returns Result.failure(Exception("Network error"))

        // When
        val result = repository.loadCountriesDto()

        // Then
        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message) // Verify that the error message matches
    }

    @Test
    fun `fetchCountryDetails should return a success result with country details`(): Unit = runBlocking {
        // Given

        val mockCountries = listOf(
            CountryDto(CountryDto.Name("France"), listOf("Paris"), 67000000, 640679.0, "🇫🇷")
        )


        coEvery{remoteDataSource.fetchCountryDtoByName("France")} returns Result.success(mockCountries)

        //When
        val result = repository.loadCountryDtoDetails("France")

        //Then
        assertTrue(result.isSuccess)
        assertEquals("France", result.getOrNull()?.firstOrNull()?.name?.common)
    }



    @Test
    fun `fetchCountryDetails should return a failure result when the remote call fails`() = runBlocking {
        // Given
        coEvery{remoteDataSource.fetchCountryDtoByName("nonExistentCountry")} returns Result.failure(Exception("Country not found"))

        // When
        val result = repository.loadCountryDtoDetails("nonExistentCountry")

        // Then
        assertTrue(result.isFailure)
        assertEquals("Country not found", result.exceptionOrNull()?.message) // Verify that the error message matches
    }
}