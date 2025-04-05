package com.example.myapplication.ui.viewmodel

import com.example.myapplication.data.model.Country
import com.example.myapplication.data.repository.CountryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountryDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: CountryDetailViewModel
    private val repository: CountryRepository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CountryDetailViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadCountryDetails should update state with country data on success`() = runTest {
        // Given
        val mockCountry = Country(
            name = Country.Name("Germany"),
            capital = listOf("Berlin"),
            population = 80_000_000,
            area = 357_000.0,
            flag = "🇩🇪"
        )
        coEvery { repository.loadCountryDetails("Germany") } returns Result.success(mockCountry)

        // When
        viewModel.loadCountryDetails("Germany")
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals(mockCountry, state.country)
        assertNull(state.error)
    }

    @Test
    fun `loadCountryDetails should update state with error message on failure`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { repository.loadCountryDetails("Germany") } returns Result.failure(Exception(errorMessage))

        // When
        viewModel.loadCountryDetails("Germany")
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertNull(state.country)
        assertEquals(errorMessage, state.error)
    }

    @Test
    fun `loadCountryDetails should set loading to true initially`() = runTest {
        // Given
        val mockCountry = Country(
            name = Country.Name("Germany"),
            capital = listOf("Berlin"),
            population = 80_000_000,
            area = 357_000.0,
            flag = "🇩🇪"
        )

        //when

        val initialState = viewModel.state.first()

        //Then
        val loadingState = viewModel.state.value
        assertTrue(loadingState.isLoading)
    }
}
