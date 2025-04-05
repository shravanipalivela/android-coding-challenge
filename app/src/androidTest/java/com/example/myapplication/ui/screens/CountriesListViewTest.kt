/*package com.example.myapplication.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CountriesListViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun loadingIndicator_isDisplayed_whenVisibleIsTrue() {
        composeTestRule.setContent {
            LoadingScreen(isVisible = true)
        }

        composeTestRule
            .onNodeWithTag("loadingIndicator")
            .assertIsDisplayed()
    }

}*/
