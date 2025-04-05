package com.example.myapplication.data.di

import com.example.myapplication.data.Utils.AndroidLogger
import com.example.myapplication.data.Utils.Constants
import com.example.myapplication.data.Utils.Logger
import com.example.myapplication.data.api.CountriesService
import com.example.myapplication.data.repository.CountryRepository
import com.example.myapplication.data.repository.CountryRepositoryImpl
import com.example.myapplication.data.source.RemoteDataSource
import com.example.myapplication.data.source.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Module
    @InstallIn(SingletonComponent::class)
    object CoroutineDispatchersModule {
        @Provides
        @Singleton
        fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object LoggerModule {

        @Provides
        fun provideLogger(): Logger = AndroidLogger()
    }

    @Provides
    fun provideRemoteDataSource(apiService: CountriesService, logger: Logger): RemoteDataSource {
        return RemoteDataSourceImpl(apiService, logger)
    }

    @Provides
    fun provideCountryRepository(remoteDataSource: RemoteDataSource): CountryRepository {
        return CountryRepositoryImpl(remoteDataSource)
    }

    @Provides
    internal fun provideCountriesService(): CountriesService {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)  // Increase connection timeout
            .readTimeout(30, TimeUnit.SECONDS)     // Increase read timeout
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CountriesService::class.java)
    }


}