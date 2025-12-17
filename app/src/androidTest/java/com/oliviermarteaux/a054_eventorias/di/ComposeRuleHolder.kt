package com.oliviermarteaux.a054_eventorias.di

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.oliviermarteaux.a054_eventorias.MainActivity
import io.cucumber.junit.WithJunitRule
import org.junit.Rule

@WithJunitRule
class ComposeRuleHolder {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()
}