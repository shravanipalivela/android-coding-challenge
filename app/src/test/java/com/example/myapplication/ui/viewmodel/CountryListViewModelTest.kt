package com.example.myapplication.ui.viewmodel

import com.example.myapplication.domain.model.Country
import com.example.myapplication.domain.usecase.CountryUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse

@OptIn(ExperimentalCoroutinesApi::class)
class CountryListViewModelTest {

    // Rule to replace Dispatchers.Main with a TestCoroutineDispatcher
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var usecase: CountryUseCase = mockk(relaxed = true)

    private lateinit var viewModel: CountryListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = CountryListViewModel(usecase,dispatcher = testDispatcher)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun `loadCountries should return the loading state to true when the api call is in progress`() = runTest {
        val initialState = viewModel.countriesState.first()
        assertTrue(initialState.isLoading)
        assertTrue(initialState.countries.isEmpty())
        assertNull(initialState.error)
    }

  /*  @Test
    fun `loadCountries should return the list of countries and update the loading state to false and error state to null when the api call is successful`() = runTest {
        //Given

      val mockCountries = listOf(
            Country(Country.Name("Germany"), listOf("Berlin"), 80000000, 357000.0, "🇩🇪"),
          Country(Country.Name("France"), listOf("Paris"), 67000000, 640679.0, "🇫🇷")
        )

        // Mock repository response
        coEvery { usecase.loadCountries() } returns Result.success(mockCountries)
        advanceUntilIdle()

        //When

        viewModel.loadCountries()
        advanceUntilIdle()


        //Then
        val newState = viewModel.countriesState.first { !it.isLoading }
        assertFalse(newState.isLoading)
        assertEquals(mockCountries, newState.countries)
        assertNull(newState.error)
    }

    @Test
    fun ` loadCountries should return the error message and update the loading state to false and countries to a empty list when the api call is failed`() = runTest {

        //Given
        val errorMessage = "Network error"
        coEvery { usecase.loadCountries() } returns Result.failure(Exception(errorMessage))

        //When
        viewModel.loadCountries()
        advanceUntilIdle()

        //Then
        val newState = viewModel.countriesState.first { !it.isLoading }
        assertFalse(newState.isLoading)
        assertTrue(newState.countries.isEmpty())
        assertEquals("Network error", newState.error)
    }*/
}
