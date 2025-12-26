package com.oliviermarteaux.a054_eventorias.cucumber.steps

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import com.oliviermarteaux.a054_eventorias.di.ComposeRuleHolder
import io.cucumber.java.en.Then

class HomeSteps(private val composeRuleHolder: ComposeRuleHolder) {

    private val composeRule = composeRuleHolder.composeRule

    @Then("All the events are displayed and scrollable on the screen")
    fun allTheEventsAreDisplayedAndScrollable() {
        // Check that the first events are visible
        composeRule.onNodeWithText("City Carnival").assertIsDisplayed()
        composeRule.onNodeWithText("Science Festival").isDisplayed()

        // Perform scroll action to verify it is scrollable
        composeRule.onNode(hasScrollAction()) // ensure scrollable container exists
            .performScrollToIndex(5)           // scroll to a later item, e.g., 5th item

        // Now verify the last event becomes visible
        composeRule.onNodeWithText("Farmers Market").assertIsDisplayed()
    }
}