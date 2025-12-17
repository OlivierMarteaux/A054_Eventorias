package com.oliviermarteaux.a054_eventorias.cucumber.steps

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.oliviermarteaux.a054_eventorias.di.ComposeRuleHolder
import io.cucumber.java.en.And
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

//_ This class requires picocontainer to inject dependency
class SharedCucumberSteps(private val composeRuleHolder: ComposeRuleHolder) {

    private val composeRule = composeRuleHolder.composeRule

    @When("I click on the {string} FAB button")
    @When("I click on the {string} button")
    fun iClickOnButton(fabLabel: String) {
        // Use contentDescription or tag for your FABs
        composeRule.onNodeWithContentDescription(fabLabel).performClick()
    }

    @When("I click on the {string} card")
    fun iClickOnCard(cardText: String) {
        // Use contentDescription or tag for your FABs
        composeRule.onNodeWithText(cardText).performClick()
    }

    @When("I enter {string} in the {string} field")
    fun iEnterText(name:String, textFieldLabel: String) {
        composeRule.onNodeWithText(textFieldLabel).performTextInput(name)
    }

    @And("I should see {string} added at the end of the {string} list")
    fun iShouldSeeTextInLastListItem(text: String, item: String){
        composeRule.onNodeWithText(text).assertIsDisplayed()
        val itemsNodes = composeRule.onAllNodes(hasClickAction())
        val lastItemNode = itemsNodes[itemsNodes.fetchSemanticsNodes().size - 2]
        lastItemNode.assertTextContains(text)
    }

    @And("I should see a toast message {string}")
    fun iShouldSeeText(text: String) {
        composeRule.onNodeWithText(text).assertIsDisplayed()
    }

    @Then("I should arrive on the {string} screen for the {string} item named {string}")
    fun iAmOnTheItemScreen(screen: String, item: String, itemName: String, ) {
        composeRule.onNodeWithText(itemName).assertIsDisplayed()
    }
}