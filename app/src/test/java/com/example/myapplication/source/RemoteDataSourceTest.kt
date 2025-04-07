package com.example.myapplication.source

import com.example.myapplication.data.Utils.Logger
import com.example.myapplication.data.api.CountriesService
import com.example.myapplication.data.model.CountryDto
import com.example.myapplication.data.source.RemoteDataSourceImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException


class RemoteDataSourceTest() {

    @Mock
    private lateinit var remoteDataSourceImpl: RemoteDataSourceImpl
    private var apiService: CountriesService = mockk()
    val mockLogger = mockk<Logger>(relaxed = true)

    @Before
    fun setUp() {
        remoteDataSourceImpl = RemoteDataSourceImpl(apiService,mockLogger)
    }

    @Test
    fun `fetchCountries should return success when api call is successful`() = runBlocking {

        //Given

        val mockCountries = listOf(
            CountryDto(CountryDto.Name("Germany"), listOf("Berlin"), 80000000, 357000.0, "🇩🇪"),
            CountryDto(CountryDto.Name("France"), listOf("Paris"), 67000000, 640679.0, "🇫🇷")
        )

        coEvery{apiService.getCountries()} returns(mockCountries)

        //When
        val result = remoteDataSourceImpl.fetchCountriesDto()

        //Then
        assertTrue(result.isSuccess)
        assertEquals(mockCountries, result.getOrNull())
    }

    @Test
    fun `fetchCountries should return failure when SocketTimeoutException occurs`() = runBlocking {

        //Given
        coEvery { apiService.getCountries() } throws SocketTimeoutException("Timeout")

        //When
        val result = remoteDataSourceImpl.fetchCountriesDto()

        //Then
        assertTrue(result.isFailure)
        assertEquals("Request timed out. Try again later.", result.exceptionOrNull()?.message)
    }

    @Test
    fun `fetchCountries should return failure when IOException occurs`() = runBlocking {
       //Given
        coEvery { apiService.getCountries() } throws SSLHandshakeException("Network error")

        //When
        val result = remoteDataSourceImpl.fetchCountriesDto()

        //Then
        assertTrue(result.isFailure)
        assertEquals("Network error: Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `fetchCountries should return failure when SSLHandshakeException occurs`() = runBlocking {
        //Given
        coEvery { apiService.getCountries() } throws SSLHandshakeException("SSL Handshake failed. Check API SSL certificate.")

        //When
        val result = remoteDataSourceImpl.fetchCountriesDto()

        //Then
        assertTrue(result.isFailure)
        assertEquals("Network error: SSL Handshake failed. Check API SSL certificate.", result.exceptionOrNull()?.message)
    }

    @Test
    fun `fetchCountryByName should return success when country is found`() = runBlocking {
        //Given
        val mockCountries = listOf(
            CountryDto(CountryDto.Name("Germany"), listOf("Berlin"), 80000000, 357000.0, "🇩🇪")
        )

        //When
        coEvery{apiService.getCountryByName("Germany")} returns mockCountries

        //Then
        val result = remoteDataSourceImpl.fetchCountryDtoByName("Germany")

        assertTrue(result.isSuccess)
        assertEquals(mockCountries[0], result.getOrNull()?.firstOrNull())
    }


}
