package com.janey.photo

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextReplacement
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
            composeTestRule.apply {
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
            composeTestRule.apply {
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

    @Test
    fun testWhenPhotoClicked_NavigateToDetailPage() {
        ActivityScenario.launch(MainActivity::class.java).use {
            composeTestRule.apply {
                onAllNodesWithTag(IMAGE_TAG).onFirst().performClick()

                onNodeWithTag("DetailScreen").assertIsDisplayed()
                onNodeWithText("title").assertIsDisplayed()
                onNodeWithText("2021-11-26 16:34:48").assertIsDisplayed()
                onNodeWithText("Owner1").assertIsDisplayed()
                onNodeWithText("description").assertIsDisplayed()
                onNodeWithText("#tag1 #tag2 #tag3").assertIsDisplayed()
            }
        }
    }

    @Test
    fun testWhenUserIdSearched_UserResultsAreLoaded() {
        ActivityScenario.launch(MainActivity::class.java).use {
            composeTestRule.apply {
                onNodeWithTag(SEARCH_TAG).assertTextEquals("Yorkshire")
                //results from both owners are present
                onNodeWithText("Owner1").assertExists()
                onNodeWithText("Owner2").assertExists()

                onNodeWithTag(SEARCH_TAG).performTextReplacement("@Owner1")
                onNodeWithTag(SEARCH_TAG).performImeAction()

                onNodeWithTag(SEARCH_TAG).assertTextEquals("@Owner1")
                onAllNodesWithText("Owner1").onFirst().assertExists()
                onNodeWithText("Owner2").assertDoesNotExist()
            }
        }
    }

    @Test
    fun testWhenTagSearched_TagResultsAreLoaded() {
        ActivityScenario.launch(MainActivity::class.java).use {
            composeTestRule.apply {
                onNodeWithTag(SEARCH_TAG).assertTextEquals("Yorkshire")
                onAllNodesWithText("#tag1 #tag2 #tag3").onFirst().assertExists()

                onNodeWithTag(SEARCH_TAG).performTextReplacement("#dog")
                onNodeWithTag(SEARCH_TAG).performImeAction()

                onNodeWithTag(SEARCH_TAG).assertTextEquals("#dog")
                onAllNodesWithText("#dog").onFirst().assertExists()
                onNodeWithText("#tag1 #tag2 #tag3").assertDoesNotExist()
            }
        }
    }

    @Test
    fun testWhenInvalidTermSearched_ResultsStayTheSame() {
        ActivityScenario.launch(MainActivity::class.java).use {
            composeTestRule.apply {
                onNodeWithTag(SEARCH_TAG).assertTextEquals("Yorkshire")
                onAllNodesWithText("#tag1 #tag2 #tag3").onFirst().assertExists()

                onNodeWithTag(SEARCH_TAG).performTextReplacement("#")
                onNodeWithTag(SEARCH_TAG).performImeAction()

                onNodeWithTag(SEARCH_TAG).assertTextEquals("#")
                onAllNodesWithText("#tag1 #tag2 #tag3").onFirst().assertExists()
            }
        }
    }


    companion object {
        const val SEARCH_TAG = "SearchField"
        const val IMAGE_TAG = "Image"
    }
}