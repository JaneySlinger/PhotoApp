package com.janey.photo

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenUITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun testAppLoads() {
        ActivityScenario.launch(MainActivity::class.java).use {
            composeTestRule.apply{
                onNodeWithTag(SEARCH_TAG).assertTextEquals("Yorkshire")
                //results from both owners are present
                onNodeWithText("Owner1").assertExists()
                onNodeWithText("Owner2").assertExists()
            }
        }
    }

    @Test
    fun testWhenClickOnUser_UserImagesAreLoaded() {
        ActivityScenario.launch(MainActivity::class.java).use {
            composeTestRule.apply{
                onNodeWithTag(SEARCH_TAG).assertTextEquals("Yorkshire")
                //results from both owners are present
                onNodeWithText("Owner1").assertExists()
                onNodeWithText("Owner2").assertExists()

                onNodeWithText("Owner1").performClick()

                onNodeWithTag(SEARCH_TAG).assertTextEquals("@Owner1")

                onAllNodesWithText("Owner1").onFirst().assertExists()
                onNodeWithText("Owner2").assertDoesNotExist()
            }
        }
    }
    

    companion object {
        const val SEARCH_TAG = "SearchField"
        const val PROFILE_TAG = "Profile"
    }
}