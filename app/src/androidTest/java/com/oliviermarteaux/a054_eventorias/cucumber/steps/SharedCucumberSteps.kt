package com.oliviermarteaux.a054_eventorias.cucumber.steps

import android.R.attr.button
import android.util.Log
import android.widget.TimePicker
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.oliviermarteaux.a054_eventorias.di.ComposeRuleHolder
import com.oliviermarteaux.shared.firebase.firestore.data.service.PostFirebaseApi
import io.cucumber.java.en.And
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.hamcrest.Matchers.`is`

//_ This class requires picocontainer to inject dependency
class SharedCucumberSteps(private val composeRuleHolder: ComposeRuleHolder) {

    private val composeRule = composeRuleHolder.composeRule

    @When("I click on the {string} FAB button")
    fun iClickOnFabButton(fabLabel: String) {
        // Use contentDescription or tag for your FABs
        Thread.sleep(300)
        composeRule.onNodeWithTag(fabLabel).performClick()
    }

    @When("I click on the {string} button")
    fun iClickOnButton(text: String) {
        // Use contentDescription or tag for your FABs
        composeRule.onNodeWithText(text = text, useUnmergedTree = true).performClick()
    }

    @When("I click on the button tagged {string}")
    fun iClickOnButtonTagged(tag: String) {
        // Use contentDescription or tag for your FABs
        Log.d("OM_TAG", "I click on the button tagged $tag")
        composeRule.onNodeWithTag(testTag = tag, useUnmergedTree = true).performClick()
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

    @And("I should see {string} added at the top of the {string} list")
    fun iShouldSeeTextInFirstListItem(text: String, item: String){

        // 1️⃣ Assert the post is displayed
        composeRule.onNodeWithText(text).assertIsDisplayed()
        val itemsNodes = composeRule.onAllNodes(hasClickAction())
        val lastItemNode: SemanticsNodeInteraction = itemsNodes[0]
        lastItemNode.assertTextContains(text)

        // 2️⃣ Delete the post immediately after assertion
        val postApi = PostFirebaseApi()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val postToDelete = postApi.getPostsOrderByCreationDateDesc()
                    .first()   // get first emitted Result<List<Post>>
                    .getOrNull()
                    ?.firstOrNull { it.title == text }

                postToDelete?.let { post ->
                    postApi.deletePost(post.id, post.photoUrl)
                    Log.d("OM_TAG", "Deleted test post '${post.title}' (ID: ${post.id})")
                } ?: Log.d("OM_TAG", "No post with title '$text' found to delete")
            } catch (e: Exception) {
                Log.e("OM_TAG", "Failed to delete post '$text'", e)
            }
        }
    }

    @And("I should see a toast message {string}")
    fun iShouldSeeText(text: String) {
        composeRule.onNodeWithText(text).assertIsDisplayed()
    }

    @Then("I should arrive on the {string} screen for the {string} item named {string}")
    fun iAmOnTheItemScreen(screen: String, item: String, itemName: String, ) {
        composeRule.waitUntil(5000) {
            composeRule.onNodeWithTag(itemName).isDisplayed()
        }
//        composeRule
//            .onRoot(useUnmergedTree = true)
//            .printToLog("SEMANTICS")
    }
    @When("I select {string} in the Date field")
    fun selectDate(date: String) {
        // Split date string "dd/MM/yyyy"
        val (day, month, year) = date.split("/").map { it.toInt() }

        // Click the date field to open DatePickerDialog
        composeRule.onNodeWithText("Date", useUnmergedTree = true).performClick()

        // Use Espresso to set the date in the dialog
        onView(withClassName(`is`(android.widget.DatePicker::class.java.name)))
            .inRoot(isDialog())
            .perform(PickerActions.setDate(year, month, day))

        // Click OK button
        onView(withText("OK"))
            .inRoot(isDialog())
            .perform(click())
    }
    @When("I select {string} in the Time field")
    fun selectTime(time: String) {
        // Split time string "HH:mm"
        val (hour, minute) = time.split(":").map { it.toInt() }

        // Click the time field to open TimePickerDialog
        composeRule.onNodeWithText("Time", useUnmergedTree = true).performClick()

        // Use Espresso to set the time
        onView(withClassName(`is`(TimePicker::class.java.name)))
            .inRoot(isDialog())
            .perform(PickerActions.setTime(hour, minute))

        // Click OK button
        onView(withText("OK"))
            .inRoot(isDialog())
            .perform(click())
    }

    @When("I pick the first photo from the photo library")
    fun iPickTheFirstPhotoFromLibrary() {

        composeRule.onNodeWithTag("Locale Photo Button").performClick()
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Wait for photo picker
        device.wait(
            Until.hasObject(By.pkg("com.android.providers.media")),
            1_000
        )

        // Find the first photo in the picker. Using a content description is often reliable.
        val firstPhoto = device.findObject(By.descStartsWith("Photo"))
        firstPhoto.click()
    }
}