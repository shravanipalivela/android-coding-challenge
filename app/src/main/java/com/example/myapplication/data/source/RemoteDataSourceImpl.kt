package com.example.myapplication.data.source

import com.example.myapplication.data.Utils.Logger
import com.example.myapplication.data.api.CountriesService
import com.example.myapplication.data.mapper.toCountry
import com.example.myapplication.data.model.Country
import com.example.myapplication.data.model.CountryDto
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: CountriesService,
    private val logger: Logger
) :
    RemoteDataSource {
    override suspend fun fetchCountries(): Result<List<CountryDto>> {
        return try {
            Result.success(apiService.getCountries())
        } catch (e: SocketTimeoutException) {
            logger.e("Network", "Request timed out. Try again later.", e)
            Result.failure(Exception("Request timed out. Try again later."))
        } catch (e: IOException) {
            logger.e("Network", "Network error: ${e.message}", e)
            Result.failure(Exception("Network error: ${e.message}"))
        } catch (e: SSLHandshakeException) {
            logger.e("Network", "SSL Handshake failed. Check API SSL certificate.", e)
            Result.failure(Exception("SSL Handshake failed. Check API SSL certificate."))
        } catch (e: Exception) {
            logger.e("Network", "Unexpected error: ${e.message}", e)
            Result.failure(Exception("Unexpected error: ${e.message}"))
        }
    }

    override suspend fun fetchCountryByName(name: String): Result<Country> {
        return try {
            val response = apiService.getCountryByName(name) // Returns List<CountryDto>
            val dto = response.firstOrNull()
            if (dto != null) {
                Result.success(dto.toCountry())
            } else {
                Result.failure(Exception("Country not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}