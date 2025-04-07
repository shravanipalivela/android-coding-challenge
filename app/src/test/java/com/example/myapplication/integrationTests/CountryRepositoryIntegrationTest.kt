package com.example.myapplication.integrationTests

import com.example.myapplication.data.Utils.Logger
import com.example.myapplication.data.api.CountriesService
import com.example.myapplication.data.repository.CountryRepositoryImpl
import com.example.myapplication.data.source.RemoteDataSourceImpl
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CountryRepositoryIntegrationTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var countryRepository: CountryRepositoryImpl
    private lateinit var remoteDataSource: RemoteDataSourceImpl

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(CountriesService::class.java)
        val mockLogger = mockk<Logger>(relaxed = true)
        remoteDataSource = RemoteDataSourceImpl(apiService,mockLogger)
        countryRepository = CountryRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `test fetchCountries returns a successful result`() = runBlocking {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                [
                    {
                        "name": { "common": "Germany" },
                        "capital": ["Berlin"],
                        "population": 83000000,
                        "area": 357022.0,
                        "flag": "🇩🇪"
                    }
                ]
            """)
        mockWebServer.enqueue(mockResponse)

        // When
        val result = countryRepository.loadCountriesDto()

        // Then
        //assertTrue(result.isSuccess)
        val countries = result.getOrDefault(emptyList())
        assertTrue(countries.isNotEmpty())
        assert(countries[0].name.common == "Germany")
    }

    @Test
    fun `test fetchCountryByName returns country details successfully`() = runBlocking {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                [
                    {
                        "name": { "common": "Germany" },
                        "capital": ["Berlin"],
                        "population": 83000000,
                        "area": 357022.0,
                        "flag": "🇩🇪"
                    }
                ]
            """)
        mockWebServer.enqueue(mockResponse)

        // When
        val result = countryRepository.loadCountryDtoDetails("Germany")


        // Then
        assertTrue(result.isSuccess)
        val country = result.getOrDefault(null)?.firstOrNull()
        assertNotNull(country)
        assertTrue(country?.name?.common == "Germany")
    }

    @Test
    fun `test fetchCountryByName handles error response`() = runBlocking {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(404)
            .setBody("{\"error\":\"Country not found\"}")
        mockWebServer.enqueue(mockResponse)

        // When
        val result = countryRepository.loadCountryDtoDetails("UnknownCountry")

        // Then
       assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        if (exception != null) {
            assertTrue(exception.message == "Country not found")
        }

    }
}